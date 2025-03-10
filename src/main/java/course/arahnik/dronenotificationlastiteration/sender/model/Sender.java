package course.arahnik.dronenotificationlastiteration.sender.model;

import course.arahnik.dronenotificationlastiteration.security.model.User;
import course.arahnik.dronenotificationlastiteration.station.model.DroneStation;
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
public class Sender {
    @Id
    private Long id;
    private String shopName;

    @NotNull
    @MapsId
    @JoinColumn(unique = true, name = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToOne(mappedBy = "owner", orphanRemoval = true)
    private WareHouse wareHouse;

    @ManyToOne(fetch = FetchType.EAGER)
    private DroneStation droneStation;
}
