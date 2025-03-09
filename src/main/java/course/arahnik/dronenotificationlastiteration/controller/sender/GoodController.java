package course.arahnik.dronenotificationlastiteration.controller.sender;

import course.arahnik.dronenotificationlastiteration.sender.dto.GoodDTO;
import course.arahnik.dronenotificationlastiteration.sender.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/good")
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;

    @PostMapping("/new")
    public GoodDTO newGood(GoodDTO goodDTO) {
        var good = goodService.save(goodDTO);
        return goodService.dtoFromEntity(good);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGood(@PathVariable Long id) {
        goodService.delete(id);
    }
}
