package course.arahnik.dronenotificationlastiteration.order.repository;

import course.arahnik.dronenotificationlastiteration.order.model.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}
