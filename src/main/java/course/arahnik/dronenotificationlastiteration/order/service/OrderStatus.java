package course.arahnik.dronenotificationlastiteration.order.service;

import course.arahnik.dronenotificationlastiteration.order.model.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
  @Id
  private Long id;

  @NotNull
  @MapsId
  @JoinColumn(unique = true, name = "order_id")
  @OneToOne(fetch = FetchType.EAGER)
  private Order order;

  private LocalDateTime startTime;
  private Double estimatedTimeLeft;
}
