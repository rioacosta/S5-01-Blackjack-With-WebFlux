package cat.itacademy.S5_01_Blackjack_With_WebFlux.repository;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Flux<Game> findByWinner(String winner);

    Flux<Object> findById(Long id);
}