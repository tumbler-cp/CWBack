package course.arahnik.dronenotificationlastiteration.customer.service;

import course.arahnik.dronenotificationlastiteration.customer.dto.CustomerDTO;
import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.customer.model.Subscription;
import course.arahnik.dronenotificationlastiteration.customer.repository.CustomerRepository;
import course.arahnik.dronenotificationlastiteration.customer.repository.SubscriptionRepository;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import course.arahnik.dronenotificationlastiteration.sender.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final SenderService senderService;
  private final AuthService authService;
  private final SubscriptionRepository subscriptionRepository;


  public CustomerDTO dtoFromEntity(Customer customer) {
    return CustomerDTO.builder()
            .id(customer.getId())
            .address(customer.getAddress())
            .build();
  }

  public Customer save(CustomerDTO customerDTO) {
    Customer customer = customerRepository.findById(customerDTO.getId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    customer.setAddress(customerDTO.getAddress());
    return customerRepository.save(customer);
  }

  public List<SenderDTO> getSenders() {
    var user = authService.getCurrentUser();
    if (user.getCustomer() == null) throw new RuntimeException("Customer not found");
    List<Subscription> subscriptions = subscriptionRepository.findAllByCustomer(user.getCustomer());
    return subscriptions.stream()
            .map(s -> senderService.dtoFromEntity(s.getSender()))
            .toList();
  }
}
