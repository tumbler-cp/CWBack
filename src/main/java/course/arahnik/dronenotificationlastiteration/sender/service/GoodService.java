package course.arahnik.dronenotificationlastiteration.sender.service;

import course.arahnik.dronenotificationlastiteration.exception.NotEnoughEditingRightsException;
import course.arahnik.dronenotificationlastiteration.exception.NotSenderException;
import course.arahnik.dronenotificationlastiteration.security.repository.UserRepository;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.sender.dto.GoodDTO;
import course.arahnik.dronenotificationlastiteration.sender.model.Good;
import course.arahnik.dronenotificationlastiteration.sender.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public GoodDTO dtoFromEntity(Good good) {
        return GoodDTO.builder()
            .id(good.getId())
            .name(good.getName())
            .weight(good.getWeight())
            .description(good.getDescription())
            .sender(good.getSenderDemo())
            .build();
    }

    public List<Good> findAll() {
        var user = authService.getCurrentUser();
        if (user.getSender() == null) {
            throw new NotSenderException("Вы не отправитель");
        }
        return goodRepository.findAllBySender(user.getSender());
    }

    public Good save(GoodDTO goodDTO) {
        var user = authService.getCurrentUser();
        if (user.getSender() == null) {
            throw new NotSenderException("Вы не отправитель");
        }
        Good g = Good.builder()
            .name(goodDTO.getName())
            .weight(goodDTO.getWeight())
            .description(goodDTO.getDescription())
            .sender(user.getSender())
            .senderDemo(user.getSender().getShopName())
            .build();
        return goodRepository.save(g);
    }

    public void delete(Long id) {
        var user = authService.getCurrentUser();
        if (!user.getUsername()
            .equals(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName())) {
            throw new NotEnoughEditingRightsException("У вас нет прав на удаление этого товара");
        }
        goodRepository.deleteById(id);
    }

    public Good getById(Long id) {
        var user = authService.getCurrentUser();
        return goodRepository.findByIdAndSender(id, user.getSender()).orElseThrow(
            () -> new RuntimeException("Такой товар не найден")
        );
    }

}
