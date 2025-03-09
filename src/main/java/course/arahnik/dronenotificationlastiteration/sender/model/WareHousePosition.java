package course.arahnik.dronenotificationlastiteration.sender.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WareHousePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Good good;

    @ManyToOne(fetch = FetchType.EAGER)
    private WareHouse wareHouse;

    private int quantity;

}
