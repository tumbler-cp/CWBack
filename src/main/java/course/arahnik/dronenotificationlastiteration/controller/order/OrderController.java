package course.arahnik.dronenotificationlastiteration.controller.order;

import course.arahnik.dronenotificationlastiteration.order.dto.CreateOrderRequest;
import course.arahnik.dronenotificationlastiteration.order.dto.OrderDTO;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderRepository;
import course.arahnik.dronenotificationlastiteration.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    orderService.acceptOrder(orderRepository.findById(orderDTO.getId())
            .orElseThrow(() -> new RuntimeException("Order not found")));
    return ResponseEntity.ok()
            .build();
  }

  @PostMapping("/reject")
  public ResponseEntity<?> rejectOrder(@RequestBody OrderDTO orderDTO) {
    orderService.rejectOrder(orderRepository.findById(orderDTO.getId())
            .orElseThrow(() -> new RuntimeException("Order not found")));
    return ResponseEntity.ok()
            .build();
  }

  @PostMapping("/receive")
  public ResponseEntity<?> receiveOrder(@RequestBody OrderDTO orderDTO) {
    orderService.receiveOrder(orderRepository.findById(orderDTO.getId())
            .orElseThrow(() -> new RuntimeException("Order not found")));
    return ResponseEntity.ok()
            .build();
  }

  @GetMapping("/customer/all")
  public List<OrderDTO> getOrdersCustomer() {
    return orderService.getCustomerOrders();
  }

  @GetMapping("/sender/all")
  public List<OrderDTO> getOrdersSender() {
    return orderService.getSenderOrders();
  }
}
