package course.arahnik.dronenotificationlastiteration.customer.dto;

import course.arahnik.dronenotificationlastiteration.geocoder.dto.RequestAddress;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
  private Long id;
  private String address;
}
