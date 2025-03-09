package course.arahnik.dronenotificationlastiteration.sender.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SenderDTO {
    private Long id;
    private String shopName;
}
