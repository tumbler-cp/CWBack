package course.arahnik.dronenotificationlastiteration.customer.service;

import course.arahnik.dronenotificationlastiteration.customer.dto.CustomerDTO;
import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.customer.repository.CustomerRepository;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final AuthService authService;

  public CustomerService(CustomerRepository customerRepository, AuthService authService) {
    this.customerRepository = customerRepository;
    this.authService = authService;
  }

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
}
