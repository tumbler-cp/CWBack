package course.arahnik.dronenotificationlastiteration.station.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneStationDTO {
    private Long id;
    private String address;
}
