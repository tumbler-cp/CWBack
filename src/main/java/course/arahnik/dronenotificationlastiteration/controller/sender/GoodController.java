package course.arahnik.dronenotificationlastiteration.controller.sender;

import course.arahnik.dronenotificationlastiteration.sender.dto.GoodDTO;
import course.arahnik.dronenotificationlastiteration.sender.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/good")
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;

    @GetMapping("/all")
    public List<GoodDTO> getAll() {
        var goods = goodService.findAll();
        List<GoodDTO> res = new ArrayList<>();
        for (var good : goods) {
            res.add(goodService.dtoFromEntity(good));
        }
        return res;
    }

    @PostMapping("/new")
    public GoodDTO newGood(@RequestBody  GoodDTO goodDTO) {
        var good = goodService.save(goodDTO);
        return goodService.dtoFromEntity(good);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGood(@PathVariable Long id) {
        goodService.delete(id);
    }
}
