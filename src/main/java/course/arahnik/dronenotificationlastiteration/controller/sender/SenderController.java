package course.arahnik.dronenotificationlastiteration.controller.sender;

import course.arahnik.dronenotificationlastiteration.sender.dto.GoodDTO;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import course.arahnik.dronenotificationlastiteration.sender.repository.GoodRepository;
import course.arahnik.dronenotificationlastiteration.sender.repository.SenderRepository;
import course.arahnik.dronenotificationlastiteration.sender.service.GoodService;
import course.arahnik.dronenotificationlastiteration.sender.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sender")
@RequiredArgsConstructor
public class SenderController {

    private final SenderService senderService;
    private final GoodRepository goodRepository;
    private final SenderRepository senderRepository;
    private final GoodService goodService;

    @GetMapping("/current")
    public SenderDTO me() {
        return senderService.dtoFromEntity(senderService.getCurrentSender());
    }

    @PostMapping("/update")
    public SenderDTO update(@RequestBody SenderDTO senderDTO) {
        return senderService.dtoFromEntity(senderService.save(senderDTO));
    }

    @GetMapping("/goods")
    public List<GoodDTO> goods(@RequestParam Long id) {
        return goodRepository.findAllBySender(
                senderRepository.findById(id).orElseThrow(RuntimeException::new)
        ).stream().map(goodService::dtoFromEntity).toList();
    }
}
