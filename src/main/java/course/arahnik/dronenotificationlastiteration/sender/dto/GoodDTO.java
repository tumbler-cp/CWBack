package course.arahnik.dronenotificationlastiteration.sender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodDTO {
    private Long id;
    private String name;
    private String description;
    private Double weight;
    private String sender;
}
