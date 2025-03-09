package course.arahnik.dronenotificationlastiteration.sender.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double weight;
    private String description;

    private String senderDemo;

    @ManyToOne
    private Sender sender;

    @OneToMany(mappedBy = "good")
    private Set<WareHousePosition> positions;

}
