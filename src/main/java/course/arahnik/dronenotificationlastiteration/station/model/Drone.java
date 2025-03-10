package course.arahnik.dronenotificationlastiteration.station.model;

import course.arahnik.dronenotificationlastiteration.order.model.Order;
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
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private DroneStation currentStation;

  @JoinColumn(unique = true)
  @OneToOne(fetch = FetchType.EAGER)
  private Order currentOrder;
}
