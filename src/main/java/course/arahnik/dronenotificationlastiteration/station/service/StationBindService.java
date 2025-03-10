package course.arahnik.dronenotificationlastiteration.station.service;

import course.arahnik.dronenotificationlastiteration.exception.UserNotVerifiedException;
import course.arahnik.dronenotificationlastiteration.security.model.enums.SenderStatus;
import course.arahnik.dronenotificationlastiteration.security.model.enums.Verification;
import course.arahnik.dronenotificationlastiteration.security.repository.UserRepository;
import course.arahnik.dronenotificationlastiteration.security.service.UserService;
import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import course.arahnik.dronenotificationlastiteration.sender.model.WareHouse;
import course.arahnik.dronenotificationlastiteration.sender.repository.SenderRepository;
import course.arahnik.dronenotificationlastiteration.sender.repository.WareHouseRepository;
import course.arahnik.dronenotificationlastiteration.station.dto.DroneStationDTO;
import course.arahnik.dronenotificationlastiteration.station.model.DroneStation;
import course.arahnik.dronenotificationlastiteration.station.repository.DroneStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationBindService {

    private final StationTokenService stationTokenService;
    private final DroneStationRepository droneStationRepository;
    private final UserService userService;
    private final SenderRepository senderRepository;
    private final UserRepository userRepository;
    private final WareHouseRepository wareHouseRepository;

    public DroneStationDTO bind(String token, Long userID) {
        var stationUUID = stationTokenService.extractStationUUID(token);
        DroneStation station = droneStationRepository.getDroneStationByUuid(UUID.fromString(stationUUID));

        var user = userService.getUserById(userID);

        if (user.getVerification()
            .equals(Verification.UNVERIFIED)) {
            throw new UserNotVerifiedException("Вы должны быть подтверждённым пользователем для данной операции");
        }

        Sender newSender = Sender.builder()
            .user(user)
            .droneStation(station)
            .build();
        var s = senderRepository.save(newSender);
        user.setSenderStatus(SenderStatus.APPROVED);
        user.setSender(s);

        var u = userRepository.save(user);

        WareHouse wareHouse = WareHouse.builder()
            .owner(u.getSender())
            .build();
        var w = wareHouseRepository.save(wareHouse);

        u.getSender()
            .setWareHouse(w);

        senderRepository.save(u.getSender());

        return DroneStationDTO.builder()
            .id(station.getId())
            .address(station.getAddress())
            .build();
    }
}
