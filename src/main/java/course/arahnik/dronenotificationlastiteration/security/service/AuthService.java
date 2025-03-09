package course.arahnik.dronenotificationlastiteration.security.service;

import course.arahnik.dronenotificationlastiteration.security.dto.AuthRequest;
import course.arahnik.dronenotificationlastiteration.security.dto.AuthTokenDTO;
import course.arahnik.dronenotificationlastiteration.security.model.User;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Role;
import course.arahnik.dronenotificationlastiteration.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthTokenDTO register(AuthRequest authRequest) {
        var user = User
            .builder()
            .username(authRequest.getUsername())
            .password(passwordEncoder.encode(authRequest.getPassword()))
            .role(Role.USER)
            .build();
        user = userRepository.save(user);
        var token = jwtService.generateToken(user);

        return AuthTokenDTO
            .builder()
            .token(token)
            .build();
    }

    public AuthTokenDTO login(AuthRequest authRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
            )
        );
        var user = userRepository.findByUsername(authRequest.getUsername());
        var token = jwtService.generateToken(user);
        return AuthTokenDTO
            .builder()
            .token(token)
            .build();
    }

    public UserDetails getCurrentUserDetails() {
        return userRepository.findByUsername(SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName());
    }

    public User getCurrentUser() {
        return userRepository.findUByUsername(
                SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName()
            )
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
