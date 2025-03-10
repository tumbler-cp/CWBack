package course.arahnik.dronenotificationlastiteration.order.repository;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.order.model.Order;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Object findBySender(Sender sender);

  List<Order> findAllBySender(Sender sender);

  List<Order> findAllByCustomer(Customer customer);

  Order findById(Long id);
}
