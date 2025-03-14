package course.arahnik.dronenotificationlastiteration.order.service;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.customer.repository.CustomerRepository;
import course.arahnik.dronenotificationlastiteration.exception.NotEnoughRightsException;
import course.arahnik.dronenotificationlastiteration.exception.TooFarException;
import course.arahnik.dronenotificationlastiteration.exception.TooHeavyException;
import course.arahnik.dronenotificationlastiteration.geocoder.model.Coordinates;
import course.arahnik.dronenotificationlastiteration.geocoder.service.GeocoderService;
import course.arahnik.dronenotificationlastiteration.order.dto.CreateOrderRequest;
import course.arahnik.dronenotificationlastiteration.order.dto.OrderDTO;
import course.arahnik.dronenotificationlastiteration.order.model.Order;
import course.arahnik.dronenotificationlastiteration.order.model.OrderPosition;
import course.arahnik.dronenotificationlastiteration.order.model.enums.OrderAcceptance;
import course.arahnik.dronenotificationlastiteration.order.model.enums.OrderStage;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderPositionRepository;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderRepository;
import course.arahnik.dronenotificationlastiteration.order.repository.OrderStatusRepository;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.sender.model.Good;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import course.arahnik.dronenotificationlastiteration.sender.model.WareHousePosition;
import course.arahnik.dronenotificationlastiteration.sender.repository.GoodRepository;
import course.arahnik.dronenotificationlastiteration.sender.repository.WareHousePositionRepository;
import course.arahnik.dronenotificationlastiteration.station.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final AuthService authService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final GoodRepository goodRepository;
  private final OrderPositionRepository orderPositionRepository;
  private final WareHousePositionRepository wareHousePositionRepository;
  private final DroneService droneService;
  private final OrderStatusRepository orderStatusRepository;
  private final GeocoderService geocoderService;

  public OrderDTO dtoFromEntity(Order order) {
    return OrderDTO.builder()
            .id(order.getId())
            .orderDate(order.getOrderDate())
            .acceptance(order.getAcceptance())
            .stage(order.getStage())
            .sender(order.getSender()
                    .getShopName())
            .destination(order.getCustomer()
                    .getAddress())
            .build();
  }

  public List<OrderDTO> getSenderOrders() {
    var user = authService.getCurrentUser();
    var orders = orderRepository.findAllBySender(user.getSender());
    List<OrderDTO> ret = new ArrayList<>();
    for (var o : orders) {
      ret.add(dtoFromEntity(o));
    }
    return ret;
  }

  public void receiveOrder(Order order) {
    var user = authService.getCurrentUser();
    if (user.getCustomer() != order.getCustomer()) {
      throw new NotEnoughRightsException("Это не вы");
    }
    var status = orderStatusRepository.findByOrder(order)
            .orElseThrow(RuntimeException::new);
    Instant i = status.getStartTime().atZone(ZoneId.systemDefault()).toInstant();
//    if (System.currentTimeMillis() - i.toEpochMilli() < status.getEstimatedTimeLeft() * 3600000) {
//      return;
//    }
    order.setStage(OrderStage.COMPLETED);
    var o = orderRepository.save(order);
    dtoFromEntity(o);
  }

  public List<OrderDTO> getCustomerOrders() {
    var user = authService.getCurrentUser();
    List<Order> orders = orderRepository.findAllByCustomer(user.getCustomer());
    List<OrderDTO> ret = new ArrayList<>();
    for (var o : orders) {
      ret.add(dtoFromEntity(o));
    }
    return ret;
  }

  @Transactional
  public void acceptOrder(Order order) {
    var user = authService.getCurrentUser();
    if (user.getCustomer() != order.getCustomer()) throw new RuntimeException("Это не ваш заказ");
    try {
      if (droneService.assignOrder(order.getSender()
              .getDroneStation(), order) == null) {
        rejectOrder(order);
        return;
      }
    } catch (TooFarException | TooHeavyException e) {
      rejectOrder(order);
      throw e;
    }

    var station = order.getSender()
            .getDroneStation();
    String orderDest = order.getCustomer()
            .getAddress();
    String stationAddr = station.getAddress();

    Coordinates customerCoor = geocoderService.forwardGeocode(orderDest);
    Coordinates stationCoor = geocoderService.forwardGeocode(stationAddr);

    order.setAcceptance(OrderAcceptance.ACCEPTED);
    order.setStage(OrderStage.DELIVERY);
    orderRepository.save(order);
    OrderStatus status = OrderStatus
            .builder()
            .order(order)
            .startTime(LocalDateTime.now())
            .estimatedTimeLeft(
                    geocoderService.calculateDistance(customerCoor.getLatitude(), customerCoor.getLongitude(),
                            stationCoor.getLatitude(), stationCoor.getLongitude()) / 20
            )
            .build();
    orderStatusRepository.save(status);
  }

  @Transactional
  public void rejectOrder(Order order) {
    var user = authService.getCurrentUser();
    if (user.getCustomer() != order.getCustomer()) throw new RuntimeException("Это не ваш заказ");
    order.setAcceptance(OrderAcceptance.REJECTED);
    var ignored = orderPositionRepository.findAllByOrder(order)
            .stream()
            .peek(
                    position -> {
                      Good good = position.getGood();
                      int quantity = position.getQuantity();
                      WareHousePosition p = wareHousePositionRepository.findByGood(good);
                      p.setQuantity(p.getQuantity() + quantity);
                      wareHousePositionRepository.save(p);
                    }
            );
    order.setStage(OrderStage.COMPLETED);
    orderRepository.save(order);
  }

  @Transactional
  public OrderDTO createOrder(CreateOrderRequest request) {
    Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Такого получателя нет"));
    Sender sender = goodRepository.findById(request.getPositions()
                    .get(0)
                    .getGoodID())
            .orElseThrow(() -> new RuntimeException("BAD REQUEST"))
            .getSender();

    Order order = Order.builder()
            .orderDate(LocalDateTime.now())
            .sender(sender)
            .customer(customer)
            .stage(OrderStage.CREATED)
            .build();
    order = orderRepository.save(order);
    final Order o = order;
    List<OrderPosition> positions = request.getPositions()
            .stream()
            .map(positionDTO -> {
              Good good = goodRepository.findById(positionDTO.getGoodID())
                      .orElseThrow(() -> new RuntimeException("Такой товар не найден"));
              WareHousePosition position = wareHousePositionRepository.findByGood(good);
              position.setQuantity(position.getQuantity() - positionDTO.getQuantity());
              wareHousePositionRepository.save(position);
              return OrderPosition.builder()
                      .order(o)
                      .good(good)
                      .quantity(positionDTO.getQuantity())
                      .build();

            })
            .toList();
    orderPositionRepository.saveAll(positions);

    return dtoFromEntity(o);
  }
}
