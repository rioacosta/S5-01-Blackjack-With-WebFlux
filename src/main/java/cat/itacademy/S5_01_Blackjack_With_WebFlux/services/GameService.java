package cat.itacademy.S5_01_Blackjack_With_WebFlux.services;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.RankingResponseDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions.GameAlreadyFinishedException;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions.GameNotFoundException;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Cards;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Game;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Player;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepo;

    public Mono<Game> createGame(Player player) {
        return gameRepo.save(new Game(player.getId()));
    }

    public Mono<Game> play(String gameId, boolean askForCard) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found")))
                .flatMap(game -> {
                    if (game.isFinished()) {
                        return Mono.error(new GameAlreadyFinishedException("Game finished"));
                    }

                    // Verificar blackjack natural al inicio
                    if (handleNaturalBlackjack(game)) {
                        game.setFinished(true);
                        return gameRepo.save(game);
                    }

                    if (askForCard) {
                        return playerTurn(game);
                    } else {
                        return dealerTurn(game);
                    }
                });
    }

    private boolean handleNaturalBlackjack(Game game) {
        boolean playerBlackjack = game.isNaturalBlackjack(game.getPlayerCards());
        boolean dealerBlackjack = game.isNaturalBlackjack(game.getCroupierCards());

        if (playerBlackjack || dealerBlackjack) {
            if (playerBlackjack && dealerBlackjack) {
                game.setWinner("DRAW");
            } else if (playerBlackjack) {
                game.setWinner("PLAYER");
            } else {
                game.setWinner("CROUPIER");
            }
            return true;
        }
        return false;
    }

    private Mono<Game> playerTurn(Game game) {
        Cards newCard = game.drawCard();
        game.addPlayerCards(newCard);

        if (game.getPlayerScore() > 21) {
            game.setWinner("CROUPIER");
            game.setFinished(true);
        }
        return gameRepo.save(game);
    }

    private Mono<Game> dealerTurn(Game game) {
        if (game.getCroupierScore() >= 17) {
            return finishGame(game);
        } else {
            Cards newCard = game.drawCard();
            game.addCroupierCards(newCard);
            return Mono.defer(() -> dealerTurn(game));
        }
    }

    private Mono<Game> finishGame(Game game) {
        int playerScore = game.getPlayerScore();
        int dealerScore = game.getCroupierScore();

        if (dealerScore > 21) {
            game.setWinner("PLAYER");
        } else if (playerScore > dealerScore) {
            game.setWinner("PLAYER");
        } else if (dealerScore > playerScore) {
            game.setWinner("CROUPIER");
        } else {
            game.setWinner("DRAW");
        }

        game.setFinished(true);
        return gameRepo.save(game);
    }

    public Mono<Void> delete(String id) {
        return gameRepo.deleteById(id);
    }

    public Mono<Game> getGame(String id) {
        return gameRepo.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with ID " + id + " not found")));
    }

    public Flux<Game> getAllGames() {
        return gameRepo.findAll();
    }

    public Flux<Object> getGamesById(Long id) {
        return gameRepo.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("No games found with ID " + id)));
    }

    public Flux<RankingResponseDTO> getPlayersIDsOrderedByWins() {
        return gameRepo.findByWinner("PLAYER")
                .groupBy(Game::getPlayerId)
                .flatMap(groupedFlux -> groupedFlux.count()
                        .map(count -> RankingResponseDTO.builder()
                                .userId(groupedFlux.key())
                                .wins(count)
                                .build()))
                .sort((a, b) -> Long.compare(b.getWins(), a.getWins()));
    }
}
