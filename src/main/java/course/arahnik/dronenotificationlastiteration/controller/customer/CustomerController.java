package course.arahnik.dronenotificationlastiteration.controller.customer;

import course.arahnik.dronenotificationlastiteration.customer.dto.CustomerDTO;
import course.arahnik.dronenotificationlastiteration.customer.service.CustomerService;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final AuthService authService;

  @GetMapping("/current")
  public CustomerDTO getCurrentCustomer() {
    return customerService.dtoFromEntity(
            authService.getCurrentUser()
                    .getCustomer()
    );
  }

  @PostMapping("/update")
  public CustomerDTO update(@RequestBody CustomerDTO customerDTO) {
    return customerService.dtoFromEntity(
            customerService.save(customerDTO)
    );
  }

  @GetMapping("/subs")
  public List<SenderDTO> getFollowing() {
    return customerService.getSenders();
  }
}
