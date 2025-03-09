package course.arahnik.dronenotificationlastiteration.sender.repository;

import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JpaRepository<Sender, Long> {
}
