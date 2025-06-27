package cat.itacademy.S5_01_Blackjack_With_WebFlux.services;


import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.RankingResponseDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions.GameAlreadyFinishedException;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions.GameNotFoundException;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Card;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Game;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Player;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepo;

    public Mono<Game> createGame(Player player) {
        Game game = new Game(player.getId());
        return gameRepo.save(game);
    }

    public Mono<Game> play(String gameId, boolean askForCard) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with ID " + gameId + " not found")))
                .flatMap(gameFound -> {
                    if (gameFound.isFinished()) {
                        return Mono.error(new GameAlreadyFinishedException("Game with ID " + gameId + " is already finished"));
                    }

                    List<Card> cardsAlreadySelected = new java.util.ArrayList<>();
                    cardsAlreadySelected.addAll(gameFound.getPlayerCards());
                    cardsAlreadySelected.addAll(gameFound.getCroupierCards());

                    if (askForCard) {
                        // TURNO DEL JUGADOR
                        Card newCard = Card.generateCard(cardsAlreadySelected);
                        gameFound.addPlayerCards(newCard);

                        int playerScore = gameFound.getPlayerScore();

                        if (playerScore > 21) {
                            gameFound.setWinner("CROUPIER");
                            gameFound.setFinished(true);
                        }

                    } else {

                        // TURNO DEL CROUPIER
                        while (gameFound.getCroupierScore() < 17) {
                            Card newCard = Card.generateCard(cardsAlreadySelected);
                            cardsAlreadySelected.add(newCard);
                            gameFound.addCroupierCards(newCard);
                        }

                        if (gameFound.getCroupierScore() < 21) { //17 to 20
                            while (gameFound.getCroupierScore() < gameFound.getPlayerScore()) {
                                Card newCard = Card.generateCard(cardsAlreadySelected);
                                cardsAlreadySelected.add(newCard);
                                gameFound.addCroupierCards(newCard);
                            }
                        }

                        if (gameFound.getCroupierScore() == gameFound.getPlayerScore()) {
                            gameFound.setWinner("DRAW");
                        } else if (gameFound.getCroupierScore() > 21) {
                            gameFound.setWinner("PLAYER");
                        } else {
                            gameFound.setWinner("CROUPIER");
                        }
                        gameFound.setFinished(true);
                    }
                    return gameRepo.save(gameFound);
                });
    }

    public void delete(String id) {
        gameRepo.deleteById(id);
    }

    public Mono<Game> getGame(String id) {
        return gameRepo.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with ID " + id + " not found")));
    }

    public Flux<Game> getAllGames() {
        return gameRepo.findAll();
    }

    public Flux<Object> getGamesByPlayerId(Long id) {
        return gameRepo.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("No games found for player with ID " + id)));
    }

    public Flux<RankingResponseDTO> getPlayersIDsOrderedByWins() {
        return gameRepo.findByWinner("PLAYER")
                .groupBy(Game::getPlayerId)
                .flatMap(groupedFlux -> groupedFlux.count()
                        .map(count -> RankingResponseDTO.builder().userId(groupedFlux.key()).wins(count).build()))
                .sort((a, b) -> Long.compare(b.getWins(), a.getWins()));
    }

}

