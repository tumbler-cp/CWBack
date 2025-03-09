package course.arahnik.dronenotificationlastiteration.station.service;

import course.arahnik.dronenotificationlastiteration.station.dto.DroneStationDTO;
import course.arahnik.dronenotificationlastiteration.station.dto.StationTokenDTO;
import course.arahnik.dronenotificationlastiteration.station.repository.DroneStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneStationService {
    private final DroneStationRepository droneStationRepository;
    private final StationTokenService stationTokenService;

    public List<DroneStationDTO> getStations() {
        var retA = droneStationRepository.findAll();
        List<DroneStationDTO> retB = new ArrayList<>();
        for (var station : retA) {
            retB.add(DroneStationDTO.builder()
                .id(station.getId())
                .address(station.getAddress())
                .build());
        }
        return retB;
    }

    public StationTokenDTO generateToken(Long stationId) {
        return StationTokenDTO.builder()
            .token(stationTokenService.generateToken(
                droneStationRepository.findById(stationId)
                    .orElseThrow(RuntimeException::new)
            ))
            .build();
    }
}
