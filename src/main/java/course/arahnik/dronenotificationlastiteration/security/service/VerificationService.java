package course.arahnik.dronenotificationlastiteration.security.service;

import course.arahnik.dronenotificationlastiteration.exception.PhoneNotSetException;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Verification;
import course.arahnik.dronenotificationlastiteration.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public void verifyUser(String username) {
        var user = userService.getUserByUsername(username);
        if (user.getPhone() == null || user.getPhone()
            .isEmpty()) {
            throw new PhoneNotSetException("У вас нет номера телефона");
        } else {
            user.setVerification(Verification.VERIFIED);
            userRepository.save(user);
        }
    }
}
