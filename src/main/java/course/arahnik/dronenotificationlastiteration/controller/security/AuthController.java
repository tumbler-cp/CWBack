package course.arahnik.dronenotificationlastiteration.controller.security;

import course.arahnik.dronenotificationlastiteration.security.dto.AuthRequest;
import course.arahnik.dronenotificationlastiteration.security.dto.AuthTokenDTO;
import course.arahnik.dronenotificationlastiteration.security.dto.UserDTO;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.security.service.UserService;
import course.arahnik.dronenotificationlastiteration.security.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final VerificationService verificationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthTokenDTO> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDTO> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @GetMapping("/me")
    public UserDTO me() {
        var user = authService.getCurrentUser();
        return userService.dtoFromUser(user);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyRequest() {
        verificationService.verifyUser(
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()
        );
        return ResponseEntity.ok()
            .build();
    }

    @PostMapping("/update")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        var user = userService.save(userDTO);
        return userService.dtoFromUser(user);
    }

}
