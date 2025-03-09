package course.arahnik.dronenotificationlastiteration.station.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneStation {
    @Id
    private Long id;
    private UUID uuid;
    private String address;
}
