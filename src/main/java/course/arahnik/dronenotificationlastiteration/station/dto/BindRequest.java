package course.arahnik.dronenotificationlastiteration.station.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BindRequest {
    private Long userID;
    private StationTokenDTO token;
}
