package cat.itacademy.S5_01_Blackjack_With_WebFlux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @Column("id")
    private Long id;
    @Column("userName")
    private String userName;

    public Player(String userName) {
        this.userName = userName;
    }
}

