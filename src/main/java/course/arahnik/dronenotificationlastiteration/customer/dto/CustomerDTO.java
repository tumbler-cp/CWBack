package course.arahnik.dronenotificationlastiteration.customer.dto;

import course.arahnik.dronenotificationlastiteration.geocoder.dto.RequestAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
  private Long id;
  private String address;
}
