package course.arahnik.dronenotificationlastiteration.security.service;

import course.arahnik.dronenotificationlastiteration.exception.NotEnoughEditingRightsException;
import course.arahnik.dronenotificationlastiteration.exception.UserNotFoundException;
import course.arahnik.dronenotificationlastiteration.exception.WrongFormatException;
import course.arahnik.dronenotificationlastiteration.security.dto.UserDTO;
import course.arahnik.dronenotificationlastiteration.security.model.User;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Verification;
import course.arahnik.dronenotificationlastiteration.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final Pattern PHONE_PATTERN = Pattern.compile(
          "^((\\+7|7|8)[\\s-]?)?((\\(\\d{3}\\))|\\d{3})[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}$"
  );

  private boolean isValidPhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      return false;
    }
    return PHONE_PATTERN.matcher(phoneNumber)
            .matches();
  }

  public User getUserById(Long userID) {
    return userRepository.findUById(userID)
            .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userID + " не найден"));
  }

  public User getUserByUsername(String username) {
    return userRepository.findUByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("Пользователь " + username + " не найден!"));
  }

  @Transactional
  public User save(UserDTO userDTO) {
    var user = getUserByUsername(userDTO.getUsername());
    if (!Objects.equals(user.getUsername(), SecurityContextHolder.getContext()
            .getAuthentication()
            .getName())) {
      throw new NotEnoughEditingRightsException("Вы не этот пользователь");
    }
    if (!isValidPhoneNumber(userDTO.getPhone())) {
      throw new WrongFormatException("Неправильный формат номера телефона");
    }
    user.setEmail(userDTO.getEmail());
    user.setPhone(userDTO.getPhone());
    userRepository.save(user);
    return user;
  }

  public UserDTO dtoFromUser(User user) {
    return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .phone(user.getPhone())
            .verified(user.getVerification() != null && user.getVerification()
                    .equals(Verification.VERIFIED))
            .role(user.getRole())
            .build();
  }
}
