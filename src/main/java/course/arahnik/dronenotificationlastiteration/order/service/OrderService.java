package course.arahnik.dronenotificationlastiteration.order.service;

import course.arahnik.dronenotificationlastiteration.order.dto.OrderDTO;
import course.arahnik.dronenotificationlastiteration.order.model.Order;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderRepository;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final UserService userService;
  private final AuthService authService;
  private final OrderRepository orderRepository;

  public List<OrderDTO> getSenderOrders() {
    var user = authService.getCurrentUser();
    var orders = orderRepository.findAllBySender(user.getSender());
    List<OrderDTO> ret = new ArrayList<>();
    for (var o : orders) {
      ret.add(OrderDTO
              .builder().id(o.getId())
              .orderDate(o.getOrderDate())
              .acceptance(o.getAcceptance())
              .sender(o.getSender().getShopName())
              .destination(o.getCustomer().getAddress())
              .build());
    }
    return ret;
  }
}
