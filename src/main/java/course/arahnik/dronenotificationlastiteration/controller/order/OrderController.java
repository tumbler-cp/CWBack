package course.arahnik.dronenotificationlastiteration.controller.order;

import course.arahnik.dronenotificationlastiteration.order.dto.CreateOrderRequest;
import course.arahnik.dronenotificationlastiteration.order.dto.OrderDTO;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderRepository;
import course.arahnik.dronenotificationlastiteration.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final OrderRepository orderRepository;

  @PostMapping("/create")
  public ResponseEntity<OrderDTO> create(@RequestBody CreateOrderRequest request) {
    return ResponseEntity.ok(orderService.createOrder(request));
  }

  @PostMapping("/accept")
  public ResponseEntity<?> acceptOrder(@RequestBody OrderDTO orderDTO) {
    orderService.acceptOrder(orderRepository.findById(orderDTO.getId()));
    return ResponseEntity.ok()
            .build();
  }

  @PostMapping("/reject")
  public ResponseEntity<?> rejectOrder(@RequestBody OrderDTO orderDTO) {
    orderService.rejectOrder(orderRepository.findById(orderDTO.getId()));
    return ResponseEntity.ok()
            .build();
  }
}
