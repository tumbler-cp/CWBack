package course.arahnik.dronenotificationlastiteration.order.repository;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import course.arahnik.dronenotificationlastiteration.order.model.Order;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Object findBySender(Sender sender);

  List<Order> findAllBySender(Sender sender);

  List<Order> findAllByCustomer(Customer customer);

  @NonNull
  Optional<Order> findById(@NonNull Long id);
}
