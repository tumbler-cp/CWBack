package course.arahnik.dronenotificationlastiteration.sender.repository;

import course.arahnik.dronenotificationlastiteration.sender.model.Good;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {
    List<Good> findAllBySender(Sender sender);
    Optional<Good> findByIdAndSender(Long id, Sender sender);
}
