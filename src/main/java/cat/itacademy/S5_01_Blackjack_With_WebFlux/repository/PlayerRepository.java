package cat.itacademy.S5_01_Blackjack_With_WebFlux.repository;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Player;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, Long> {
    Mono<Player> findByUserName(String userName);
}
