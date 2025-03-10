package course.arahnik.dronenotificationlastiteration.geocoder.service;

import course.arahnik.dronenotificationlastiteration.geocoder.model.Coordinates;
import course.arahnik.dronenotificationlastiteration.geocoder.repository.CoordinatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GeocoderService {
  private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/";
  private static final double EARTH_RADIUS = 6371.0; // Радиус Земли в км

  private final RestTemplate restTemplate = new RestTemplate();
  private final CoordinatesRepository coordinatesRepository;

  public Coordinates forwardGeocode(String address) {
    return coordinatesRepository.findByAddress(address)
            .orElseGet(() -> fetchAndSaveCoordinates(address));
  }

  private Coordinates fetchAndSaveCoordinates(String address) {
    String url = String.format("%ssearch?q=%s&format=json&addressdetails=1&limit=1", NOMINATIM_URL, address);
    ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
    if (response.getBody() != null && response.getBody().length > 0) {
      LinkedHashMap<String, Object> result = (LinkedHashMap<String, Object>) response.getBody()[0];
      double lat = Double.parseDouble(result.get("lat")
              .toString());
      double lon = Double.parseDouble(result.get("lon")
              .toString());
      Coordinates coordinates = Coordinates.builder()
              .address(address)
              .latitude(lat)
              .longitude(lon)
              .build();
      return coordinatesRepository.save(coordinates);
    }
    throw new RuntimeException("Некорректный формат адреса");
  }

  public Optional<Coordinates> reverseGeocode(double lat, double lon) {
    String url = String.format("%sreverse?lat=%f&lon=%f&format=json&addressdetails=1", NOMINATIM_URL, lat, lon);
    LinkedHashMap<String, Object> response = restTemplate.getForObject(url, LinkedHashMap.class);
    if (response != null && response.containsKey("lat") && response.containsKey("lon")) {
      double parsedLat = Double.parseDouble(response.get("lat")
              .toString());
      double parsedLon = Double.parseDouble(response.get("lon")
              .toString());
      return Optional.of(
              Coordinates.builder()
                      .latitude(parsedLat)
                      .longitude(parsedLon)
                      .build()
      );
    }
    return Optional.empty();
  }

  public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    return haversineFormula(lat1, lon1, lat2, lon2);
  }

  public double estimateTime(double lat1, double lon1, double lat2, double lon2, double speedKmh) {
    if (speedKmh <= 0) {
      throw new IllegalArgumentException("Speed must be greater than 0");
    }
    return haversineFormula(lat1, lon1, lat2, lon2) / speedKmh;
  }

  private double haversineFormula(double lat1, double lon1, double lat2, double lon2) {
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return EARTH_RADIUS * c;
  }

}
