package course.arahnik.dronenotificationlastiteration.controller.sender;

import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderTokenDTO;
import course.arahnik.dronenotificationlastiteration.sender.service.SenderBindService;
import course.arahnik.dronenotificationlastiteration.station.dto.BindRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sender/bind")
@RequiredArgsConstructor
public class SenderBindController {

  private final SenderBindService senderBindService;

  @PostMapping("/request")
  public ResponseEntity<SenderDTO> bind(@RequestBody BindRequest request) {
    return ResponseEntity.ok(senderBindService.bind(request.getToken()
            .getToken(), request.getUserID()));
  }

  @PostMapping("/token/generate")
  public ResponseEntity<SenderTokenDTO> generateToken() {
    return ResponseEntity.ok(senderBindService.generateToken());
  }
}
