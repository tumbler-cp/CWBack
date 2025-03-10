package course.arahnik.dronenotificationlastiteration.customer.service;

import course.arahnik.dronenotificationlastiteration.customer.dto.CustomerDTO;
import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  public CustomerDTO dtoFromEntity(Customer customer) {
    return CustomerDTO.builder()
            .id(customer.getId())
            .address(customer.getAddress())
            .build();
  }
}
