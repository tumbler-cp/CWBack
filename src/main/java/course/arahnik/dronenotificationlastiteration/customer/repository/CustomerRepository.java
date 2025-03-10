package course.arahnik.dronenotificationlastiteration.customer.repository;

import course.arahnik.dronenotificationlastiteration.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
