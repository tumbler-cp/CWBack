package course.arahnik.dronenotificationlastiteration.order.repository;

import course.arahnik.dronenotificationlastiteration.order.model.Order;
import course.arahnik.dronenotificationlastiteration.order.model.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
  List<OrderPosition> findAllByOrder(Order order);
}
