package course.arahnik.dronenotificationlastiteration.order.model;

import course.arahnik.dronenotificationlastiteration.sender.model.Good;
import course.arahnik.dronenotificationlastiteration.sender.model.WareHouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPosition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Good good;

  @ManyToOne(fetch = FetchType.EAGER)
  private Order order;

  private int quantity;
}
