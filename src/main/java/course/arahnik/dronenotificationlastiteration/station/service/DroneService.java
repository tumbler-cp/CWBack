package course.arahnik.dronenotificationlastiteration.station.service;

import course.arahnik.dronenotificationlastiteration.exception.NoDroneAvailableException;
import course.arahnik.dronenotificationlastiteration.exception.TooFarException;
import course.arahnik.dronenotificationlastiteration.geocoder.model.Coordinates;
import course.arahnik.dronenotificationlastiteration.geocoder.service.GeocoderService;
import course.arahnik.dronenotificationlastiteration.order.model.Order;
import course.arahnik.dronenotificationlastiteration.order.service.OrderService;
import course.arahnik.dronenotificationlastiteration.station.model.Drone;
import course.arahnik.dronenotificationlastiteration.station.model.DroneStation;
import course.arahnik.dronenotificationlastiteration.station.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneService {
  private final DroneRepository droneRepository;
  private final GeocoderService geocoderService;

  public Drone assignOrder(DroneStation station, Order order) {
    String orderDest = order.getCustomer()
            .getAddress();
    String stationAddr = station.getAddress();

    Coordinates customerCoor = geocoderService.forwardGeocode(orderDest);
    Coordinates stationCoor = geocoderService.forwardGeocode(stationAddr);

    if (geocoderService.calculateDistance(customerCoor.getLatitude(), customerCoor.getLongitude(),
            stationCoor.getLatitude(), stationCoor.getLongitude()) > 50) {
      throw new TooFarException("Вы находитесь слишком далеко от станции");
    }

    List<Drone> drones = droneRepository.findAllByCurrentStation(station);
    for (Drone drone : drones) {
      if (drone.getCurrentOrder() == null) {
        drone.setCurrentOrder(order);
        droneRepository.save(drone);
        return drone;
      }
    }
    throw new NoDroneAvailableException("Нет свободных дронов");
  }
}
