package course.arahnik.dronenotificationlastiteration.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
  private Long customerId;
  private List<OrderPositionDTO> positions;
}
