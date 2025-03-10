package course.arahnik.dronenotificationlastiteration.sender.service;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.customer.model.Subscription;
import course.arahnik.dronenotificationlastiteration.customer.repository.CustomerRepository;
import course.arahnik.dronenotificationlastiteration.customer.repository.SubscriptionRepository;
import course.arahnik.dronenotificationlastiteration.exception.NotSenderException;
import course.arahnik.dronenotificationlastiteration.security.model.User;
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
  private final SubscriptionRepository subscriptionRepository;

  public SenderDTO bind(String token, Long userId) {
    var senderID = senderTokenService.extractSenderId(token);
    Sender sender = senderRepository.findById(senderID)
            .orElseThrow(
                    () -> new RuntimeException("Sender not found")
            );
    User customerUser = userService.getUserById(userId);

    if (customerUser.getCustomer() == null) {
      Customer newC = Customer.builder()
              .user(customerUser)
              .build();
      customerRepository.save(newC);
    }

    Customer customer = customerUser.getCustomer();

    Subscription subscription = Subscription.builder()
            .customer(customer)
            .sender(sender)
            .build();
    subscriptionRepository.save(subscription);
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
