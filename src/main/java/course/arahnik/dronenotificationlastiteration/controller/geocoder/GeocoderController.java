package course.arahnik.dronenotificationlastiteration.controller.geocoder;

import course.arahnik.dronenotificationlastiteration.geocoder.dto.RequestAddress;
import course.arahnik.dronenotificationlastiteration.geocoder.model.Coordinates;
import course.arahnik.dronenotificationlastiteration.geocoder.service.GeocoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geocode")
@RequiredArgsConstructor
public class GeocoderController {

  private final GeocoderService geocoderService;

  @PostMapping("/forward")
  public Coordinates forward(@RequestBody RequestAddress address) {
    return geocoderService.forwardGeocode(
            address.getCountry() + " " +
                    address.getCity() + " " +
                    address.getStreet() + " " +
                    address.getHouseNumber()
     );
  }
}
