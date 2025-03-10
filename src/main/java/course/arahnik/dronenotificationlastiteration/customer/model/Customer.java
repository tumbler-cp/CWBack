package course.arahnik.dronenotificationlastiteration.customer.model;

import course.arahnik.dronenotificationlastiteration.security.model.User;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  @Id
  private Long id;

  private String address;

  @NotNull
  @MapsId
  @JoinColumn(unique = true, name = "user_id")
  @OneToOne(fetch = FetchType.EAGER)
  private User user;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Sender> following;
}
