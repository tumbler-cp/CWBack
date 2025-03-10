package course.arahnik.dronenotificationlastiteration.sender.repository;

import course.arahnik.dronenotificationlastiteration.sender.model.Good;
import course.arahnik.dronenotificationlastiteration.sender.model.WareHouse;
import course.arahnik.dronenotificationlastiteration.sender.model.WareHousePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WareHousePositionRepository extends JpaRepository<WareHousePosition, Long> {
  List<WareHousePosition> findAllByWareHouse(WareHouse wareHouse);

  WareHousePosition findByGood(Good good);
}
