package course.arahnik.dronenotificationlastiteration.sender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WareHousePositionDTO {
    private Long id;
    private GoodDTO good;
    private int quantity;
}
