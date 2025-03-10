package course.arahnik.dronenotificationlastiteration.station.repository;

import course.arahnik.dronenotificationlastiteration.station.model.Drone;
import course.arahnik.dronenotificationlastiteration.station.model.DroneStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
  List<Drone> findAllByCurrentStation(DroneStation currentStation);
}
