package course.arahnik.dronenotificationlastiteration.station.repository;

import course.arahnik.dronenotificationlastiteration.station.model.DroneStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DroneStationRepository extends JpaRepository<DroneStation, Long> {

    DroneStation getDroneStationByUuid(UUID uuid);
}
