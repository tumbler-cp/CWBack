package course.arahnik.dronenotificationlastiteration.order.model;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.order.model.enums.OrderAcceptance;
import course.arahnik.dronenotificationlastiteration.order.model.enums.OrderStage;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import jakarta.persistence.*;
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
@Table(name = "app_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderAcceptance acceptance;

  @Enumerated(EnumType.STRING)
  private OrderStage stage;

  @ManyToOne
  private Sender sender;

  private Date updateTime;

  @ManyToOne
  private Customer customer;
}
