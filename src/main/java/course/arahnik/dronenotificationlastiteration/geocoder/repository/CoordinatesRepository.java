package course.arahnik.dronenotificationlastiteration.geocoder.repository;

import course.arahnik.dronenotificationlastiteration.geocoder.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
  Optional<Coordinates> findByAddress(String address);
}
