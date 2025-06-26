package cat.itacademy.S5_01_Blackjack_With_WebFlux.services;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions.DuplicatePlayerException;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions.PlayerNotFoundException;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Player;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepo;

    public Mono<Player> create(String userName) {
        return playerRepo.findByUserName(userName)
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DuplicatePlayerException("Username already exists: " + userName));
                    } else {
                        return playerRepo.save(new Player(userName));
                    }
                });
    }

    public Mono<Player> updateName(Long id, String newName) {
        return playerRepo.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player with ID " + id + " not found")))
                .flatMap(player -> {
                    player.setUserName(newName);
                    return playerRepo.save(player);
                });
    }

    public Mono<Player> getPlayerByPlayerName(String playerName) {
        return playerRepo.findByUserName(playerName)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player with username '" + playerName + "' not found")));
    }

    public Flux<Player> getAllPlayers() {
        return playerRepo.findAll();
    }

    public Mono<Player> getPlayerById(long id) {
        return playerRepo.findById((long) id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player with ID " + id + " not found")));
    }


    public Mono<Player> updatePlayer(long id, String newName) {
        return playerRepo.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player with ID " + id + " not found")))
                .flatMap(existing -> {
                    existing.setUserName(newName);
                    return playerRepo.save(existing);
                });
    }

    public Mono<Void> deletePlayer(int id) {
        return playerRepo.findById((long) id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player with ID " + id + " not found")))
                .flatMap(player -> playerRepo.delete(player));
    }

}




