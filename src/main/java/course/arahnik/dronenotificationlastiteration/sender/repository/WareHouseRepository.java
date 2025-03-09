package course.arahnik.dronenotificationlastiteration.sender.repository;

import course.arahnik.dronenotificationlastiteration.sender.model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
}
