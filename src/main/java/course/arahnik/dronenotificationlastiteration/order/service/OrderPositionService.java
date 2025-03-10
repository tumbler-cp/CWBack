package course.arahnik.dronenotificationlastiteration.order.service;

import course.arahnik.dronenotificationlastiteration.order.model.OrderPosition;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderPositionRepository;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderRepository;
import course.arahnik.dronenotificationlastiteration.sender.repository.GoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPositionService {

  private final OrderRepository orderRepository;
  private final GoodRepository goodRepository;
  private final OrderPositionRepository orderPositionRepository;

  @Transactional
  public void createPosition(Long goodID, Long orderID, int quantity) {
    var order = orderRepository.findById(orderID).orElseThrow(
            () -> new RuntimeException("Order not found")
    );
    var good = goodRepository.findById(goodID)
            .orElseThrow(
                    () -> new RuntimeException("Такого товара не существует")
            );
    OrderPosition position = OrderPosition.builder()
            .good(good)
            .order(order)
            .quantity(quantity)
            .build();
    orderPositionRepository.save(position);
  }

  @Transactional
  public void changePosition(Long orderPositionID, int quantity) {
    var orderPosition = orderPositionRepository.findById(orderPositionID)
            .orElseThrow(() -> new RuntimeException("Нет такой позиции заказа"));
    orderPosition.setQuantity(quantity);
    orderPositionRepository.save(orderPosition);
  }

  public void deletePosition(Long orderPositionID) {
    var orderPosition = orderPositionRepository.findById(orderPositionID)
            .orElseThrow(() -> new RuntimeException("Такой позиции нет"));
    orderPositionRepository.delete(orderPosition);
  }
}
