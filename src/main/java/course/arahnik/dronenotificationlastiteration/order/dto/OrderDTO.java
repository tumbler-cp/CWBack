package course.arahnik.dronenotificationlastiteration.order.dto;

import course.arahnik.dronenotificationlastiteration.order.model.enums.OrderAcceptance;
import course.arahnik.dronenotificationlastiteration.order.model.enums.OrderStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  private Long id;
  private LocalDateTime orderDate;
  private OrderAcceptance acceptance;
  private OrderStage stage;
  private String sender;
  private String destination;
}
