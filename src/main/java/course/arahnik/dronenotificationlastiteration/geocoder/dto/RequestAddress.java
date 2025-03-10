package course.arahnik.dronenotificationlastiteration.geocoder.dto;

import lombok.Data;

@Data
public class RequestAddress {
  private String country;
  private String city;
  private String street;
  private String houseNumber;

  public String toString() {
    return country + " " + city + " " + street + " " + houseNumber;
  }
}
