package course.arahnik.dronenotificationlastiteration.sender.repository;

import course.arahnik.dronenotificationlastiteration.sender.model.WareHousePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHousePositionRepository extends JpaRepository<WareHousePosition, Long> {
}
