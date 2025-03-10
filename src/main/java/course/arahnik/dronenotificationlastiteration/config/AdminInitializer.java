package course.arahnik.dronenotificationlastiteration.config;

import course.arahnik.dronenotificationlastiteration.security.dto.AuthRequest;
import course.arahnik.dronenotificationlastiteration.security.model.User;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Role;
import course.arahnik.dronenotificationlastiteration.security.repository.UserRepository;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.security.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserService userService;
    @Value("${admin.name}")
    private String adminName;
    @Value("${admin.password}")
    private String adminPass;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (!userRepository.findAllByRole(Role.ADMIN).isEmpty()) {
            return;
        }
        authService.register(AuthRequest.builder().username(adminName).password(adminPass).build());
        var user = userService.getUserByUsername(adminName);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
