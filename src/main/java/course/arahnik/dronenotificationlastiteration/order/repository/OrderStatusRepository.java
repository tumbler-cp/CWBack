package course.arahnik.dronenotificationlastiteration.order.repository;

import course.arahnik.dronenotificationlastiteration.order.model.Order;
import course.arahnik.dronenotificationlastiteration.order.service.OrderStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
  Optional<OrderStatus> findByOrder(@NotNull Order order);
}
