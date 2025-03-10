package course.arahnik.dronenotificationlastiteration.security.repository;

import course.arahnik.dronenotificationlastiteration.security.model.User;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(@NotNull @NotBlank @Size(min = 4, max = 120) String username);

    Optional<User> findUByUsername(@NotNull @NotBlank @Size(min = 4, max = 120) String username);

    Optional<User> findUById(@NotNull Long userID);

    List<User> Role(Role role);

    List<User> findAllByRole(Role role);
}
