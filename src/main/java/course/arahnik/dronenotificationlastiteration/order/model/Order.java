package course.arahnik.dronenotificationlastiteration.order.model;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.order.model.enums.OrderAcceptance;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime orderDate;

  private OrderAcceptance acceptance;

  @ManyToOne
  private Sender sender;

  @ManyToOne
  private Customer customer;
}
