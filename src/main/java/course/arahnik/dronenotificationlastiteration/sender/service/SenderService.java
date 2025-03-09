package course.arahnik.dronenotificationlastiteration.sender.service;

import course.arahnik.dronenotificationlastiteration.security.service.UserService;
import course.arahnik.dronenotificationlastiteration.sender.dto.SenderDTO;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import course.arahnik.dronenotificationlastiteration.sender.repository.SenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SenderService {
    private final UserService userService;
    private final SenderRepository senderRepository;

    @Transactional
    public Sender save(Long userID, SenderDTO senderDTO) {
        var sender = Sender.builder()
            .shopName(senderDTO.getShopName())
            .user(
                userService.getUserById(userID)
            )
            .build();
        return senderRepository.save(sender);
    }
}
