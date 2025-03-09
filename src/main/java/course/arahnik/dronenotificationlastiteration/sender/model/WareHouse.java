package course.arahnik.dronenotificationlastiteration.sender.model;

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
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(unique = true, name = "owner_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Sender owner;

    @OneToMany(mappedBy = "wareHouse")
    private Set<WareHousePosition> positions;
}
