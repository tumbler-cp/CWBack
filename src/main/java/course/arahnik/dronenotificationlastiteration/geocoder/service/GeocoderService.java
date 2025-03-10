package course.arahnik.dronenotificationlastiteration.geocoder.service;

import course.arahnik.dronenotificationlastiteration.geocoder.model.Coordinates;
import course.arahnik.dronenotificationlastiteration.geocoder.repository.CoordinatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeocoderService {
  private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/";
  private final RestTemplate restTemplate = new RestTemplate();
  private final CoordinatesRepository coordinatesRepository;


  public Coordinates forwardGeocode(String address) {
    if (coordinatesRepository.findByAddress(address).isPresent()) {
      return coordinatesRepository.findByAddress(address).get();
    }
    String url = NOMINATIM_URL + "search?q=" + address + "&format=json&addressdetails=1&limit=1";
    ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
    if (response.getBody() != null && response.getBody().length > 0) {
      LinkedHashMap<String, Object> result = (LinkedHashMap<String, Object>) response.getBody()[0];
      double lat = Double.parseDouble(result.get("lat")
              .toString());
      double lon = Double.parseDouble(result.get("lon")
              .toString());
      Coordinates coordinates =  Coordinates.builder()
              .address(address)
              .latitude(lat)
              .longitude(lon)
              .build();
      coordinates = coordinatesRepository.save(coordinates);
      return coordinates;
    }
    throw new RuntimeException("Некорректный формат адреса");
  }

  public Map<String, Double> reverseGeocode(double lat, double lon) {
    String url = NOMINATIM_URL + "reverse?lat=" + lat + "&lon=" + lon + "&format=json&addressdetails=1";
    LinkedHashMap<String, Object> response = restTemplate.getForObject(url, LinkedHashMap.class);
    if (response != null && response.containsKey("lat") && response.containsKey("lon")) {
      double parsedLat = Double.parseDouble(response.get("lat")
              .toString());
      double parsedLon = Double.parseDouble(response.get("lon")
              .toString());
      Map<String, Double> coordinates = new HashMap<>();
      coordinates.put("latitude", parsedLat);
      coordinates.put("longitude", parsedLon);
      return coordinates;
    }
    return Collections.emptyMap();
  }

  public String calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    final double R = 6371;
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distanceKm = R * c;
    return "distance_km: " + distanceKm + ", distance_m: " + (distanceKm * 1000);
  }

  public String estimateTime(double lat1, double lon1, double lat2, double lon2, double speedKmh) {
    if (speedKmh <= 0) {
      return "Speed must be greater than 0";
    }
    final double R = 6371; // Earth radius in km
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distanceKm = R * c;
    double timeHours = distanceKm / speedKmh;
    double timeMinutes = timeHours * 60;
    return "estimated_time_hours: " + timeHours + ", estimated_time_minutes: " + timeMinutes;
  }

  public String interpolate(double start, double end, double fraction) {
    if (fraction < 0 || fraction > 1) {
      return "Fraction must be between 0 and 1";
    }
    return "value: " + (start + (end - start) * fraction);
  }
}
