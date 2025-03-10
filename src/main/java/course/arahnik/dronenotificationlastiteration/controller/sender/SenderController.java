package course.arahnik.dronenotificationlastiteration.controller.sender;

import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import course.arahnik.dronenotificationlastiteration.sender.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sender")
@RequiredArgsConstructor
public class SenderController {

    private final SenderService senderService;

    @GetMapping("/current")
    public SenderDTO me() {
        return senderService.dtoFromEntity(senderService.getCurrentSender());
    }

    @PostMapping("/update")
    public SenderDTO update(@RequestBody SenderDTO senderDTO) {
        return senderService.dtoFromEntity(senderService.save(senderDTO));
    }
}
