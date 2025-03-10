package course.arahnik.dronenotificationlastiteration.customer.model;

import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
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
public class Subscription {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Sender sender;

  @ManyToOne
  private Customer customer;
}
