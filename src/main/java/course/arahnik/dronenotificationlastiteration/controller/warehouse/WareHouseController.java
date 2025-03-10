package course.arahnik.dronenotificationlastiteration.controller.warehouse;

import course.arahnik.dronenotificationlastiteration.sender.dto.WareHousePositionDTO;
import course.arahnik.dronenotificationlastiteration.sender.service.WareHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WareHouseController {

    private final WareHouseService wareHouseService;

    @PostMapping("/new_position")
    public ResponseEntity<?> createPosition(@RequestBody WareHousePositionDTO dto) {
        wareHouseService.createPosition(dto.getGood().getId(), dto.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change_position")
    public ResponseEntity<?> changePosition(@RequestBody WareHousePositionDTO dto) {
        wareHouseService.changePosition(dto.getId(), dto.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/positions")
    public List<WareHousePositionDTO> getPositions() {
        return wareHouseService.getPositions();
    }
}
