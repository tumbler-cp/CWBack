package course.arahnik.dronenotificationlastiteration.security.dto;

import course.arahnik.dronenotificationlastiteration.security.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Boolean verified;
    private Role role;
}
