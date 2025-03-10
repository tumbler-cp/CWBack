package course.arahnik.dronenotificationlastiteration.sender.service;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.customer.repository.CustomerRepository;
import course.arahnik.dronenotificationlastiteration.exception.NotSenderException;
import course.arahnik.dronenotificationlastiteration.exception.UserNotVerifiedException;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Verification;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.security.service.UserService;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderTokenDTO;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import course.arahnik.dronenotificationlastiteration.sender.repository.SenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderBindService {
  private final SenderTokenService senderTokenService;
  private final SenderRepository senderRepository;
  private final UserService userService;
  private final CustomerRepository customerRepository;
  private final SenderService senderService;
  private final AuthService authService;

  public SenderDTO bind(String token, Long userId) {
    var senderID = senderTokenService.extractSenderId(token);
    Sender sender = senderRepository.findById(senderID)
            .orElseThrow(
                    () -> new RuntimeException("Такой отправитель не найден")
            );

    var user = userService.getUserById(userId);

    if (user.getVerification()
            .equals(Verification.UNVERIFIED)) {
      throw new UserNotVerifiedException("Пользователь должен быть подтвержденным");
    }

    if (user.getCustomer() == null) {
      Customer newCustomer = Customer.builder()
              .user(user)
              .build();
      customerRepository.save(newCustomer);
    }

    var customer = user.getCustomer();
    customer.getFollowing()
            .add(sender);
    customerRepository.save(customer);
    return senderService.dtoFromEntity(sender);
  }

  public SenderTokenDTO generateToken() {
    var user = authService.getCurrentUser();
    if (user.getSender() == null) {
      throw new NotSenderException("Вы не отправитель");
    }
    return SenderTokenDTO.builder()
            .token(
                    senderTokenService.generateToken(user.getSender())
            )
            .build();
  }
}
