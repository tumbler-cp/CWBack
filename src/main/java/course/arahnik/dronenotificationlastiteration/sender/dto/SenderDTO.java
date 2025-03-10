package course.arahnik.dronenotificationlastiteration.sender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenderDTO {
    private Long id;
    private String shopName;
}
