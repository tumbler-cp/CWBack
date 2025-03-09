package course.arahnik.dronenotificationlastiteration.controller.station;

import course.arahnik.dronenotificationlastiteration.exception.NotEnoughRightsException;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Role;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.station.dto.DroneStationDTO;
import course.arahnik.dronenotificationlastiteration.station.dto.StationTokenDTO;
import course.arahnik.dronenotificationlastiteration.station.service.DroneStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {

    private final DroneStationService droneStationService;
    private final AuthService authService;

    @GetMapping("/all")
    public List<DroneStationDTO> getAllStations() {
        return droneStationService.getStations();
    }

    @PostMapping("/{id}/token")
    public StationTokenDTO generateStationTokenDTO(@PathVariable Long id) {
        var user = authService.getCurrentUser();
        if (user.getRole() != Role.ADMIN) throw new NotEnoughRightsException("Вы не администратор");
        return droneStationService.generateToken(id);
    }
}
