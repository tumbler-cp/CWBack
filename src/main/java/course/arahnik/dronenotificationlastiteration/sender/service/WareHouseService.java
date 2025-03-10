package course.arahnik.dronenotificationlastiteration.sender.service;

import course.arahnik.dronenotificationlastiteration.exception.NotSenderException;
import course.arahnik.dronenotificationlastiteration.security.service.AuthService;
import course.arahnik.dronenotificationlastiteration.sender.dto.WareHousePositionDTO;
import course.arahnik.dronenotificationlastiteration.sender.model.WareHousePosition;
import course.arahnik.dronenotificationlastiteration.sender.repository.GoodRepository;
import course.arahnik.dronenotificationlastiteration.sender.repository.WareHousePositionRepository;
import course.arahnik.dronenotificationlastiteration.sender.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WareHouseService {


  private final AuthService authService;
  private final GoodService goodService;
  private final WareHousePositionRepository wareHousePositionRepository;
  private final GoodRepository goodRepository;
  private final WareHouseRepository wareHouseRepository;

  @Transactional
  public void createPosition(Long goodID, int quantity) {
    var user = authService.getCurrentUser();
    if (user.getSender() == null) {
      throw new NotSenderException("Вы не отправитель");
    }
    var good = goodService.getById(goodID);
    WareHousePosition position = WareHousePosition.builder()
            .good(good)
            .wareHouse(user.getSender()
                    .getWareHouse())
            .quantity(quantity)
            .build();
    var p = wareHousePositionRepository.save(position);

    goodRepository.save(good);
    wareHouseRepository.save(user.getSender()
            .getWareHouse());
  }

  @Transactional
  public void changePosition(Long positionID, int quantity) {
    var user = authService.getCurrentUser();
    if (user.getSender() == null) {
      throw new NotSenderException("Вы не отправитель");
    }
    var position = wareHousePositionRepository.findById(positionID)
            .orElseThrow(() -> new RuntimeException("Такой позиции нет"));
    position.setQuantity(quantity);
  }

  public List<WareHousePositionDTO> getPositions() {
    var user = authService.getCurrentUser();
    if (user.getSender() == null) {
      throw new NotSenderException("Вы не отправитель");
    }
    List<WareHousePosition> positions = wareHousePositionRepository.findAllByWareHouse(user.getSender()
            .getWareHouse());
    List<WareHousePositionDTO> ret = new ArrayList<>();
    for (var p : positions) {
      ret.add(WareHousePositionDTO.builder()
              .id(p.getId())
              .good(goodService.dtoFromEntity(p.getGood()))
              .quantity(p.getQuantity())
              .build());
    }
    return ret;
  }
}
