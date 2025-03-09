package course.arahnik.dronenotificationlastiteration.controller.sender;

import course.arahnik.dronenotificationlastiteration.station.dto.BindRequest;
import course.arahnik.dronenotificationlastiteration.station.dto.DroneStationDTO;
import course.arahnik.dronenotificationlastiteration.station.service.StationBindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station/bind")
@RequiredArgsConstructor
public class StationBindController {
    private final StationBindService stationBindService;

    @PostMapping("/request")
    public ResponseEntity<DroneStationDTO> bind(@RequestBody BindRequest request) {
        return ResponseEntity.ok(stationBindService.bind(request.getToken()
            .getToken(), request.getUserID()));
    }

}
