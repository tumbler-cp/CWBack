package course.arahnik.dronenotificationlastiteration.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenDTO {
    private String token;
}
