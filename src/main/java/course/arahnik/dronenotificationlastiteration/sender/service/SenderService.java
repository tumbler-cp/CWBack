package course.arahnik.dronenotificationlastiteration.sender.service;

import course.arahnik.dronenotificationlastiteration.exception.NotSenderException;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.security.service.UserService;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import course.arahnik.dronenotificationlastiteration.sender.repository.GoodRepository;
import course.arahnik.dronenotificationlastiteration.sender.repository.SenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SenderService {
    private final UserService userService;
    private final SenderRepository senderRepository;
    private final AuthService authService;
    private final GoodRepository goodRepository;

    public SenderDTO dtoFromEntity(Sender sender) {
        return SenderDTO.builder()
            .id(sender.getId())
            .shopName(sender.getShopName())
            .build();
    }

    public Sender getCurrentSender() {
        var user = authService.getCurrentUser();
        if (user.getSender() == null) {
            throw new NotSenderException("Вы не отправитель");
        }
        return user.getSender();
    }

    @Transactional
    public Sender save(SenderDTO senderDTO) {
        var user = authService.getCurrentUser();
        if (user.getSender() == null) {
            throw new NotSenderException("Вы не отправитель");
        }
        var sender = user.getSender();
        sender.setShopName(senderDTO.getShopName());
        return senderRepository.save(sender);
    }
}
