package cat.itacademy.S5_01_Blackjack_With_WebFlux.controllers;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.CreateGameRequestDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Game;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Player;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.GameService;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {
    @Mock
    private GameService gameService;
    @Mock
    private PlayerService playerService;
    @InjectMocks
    private GameController gameController;

    @Test
    void createGame() {
        String playerName = "Juan";
        CreateGameRequestDTO request = new CreateGameRequestDTO();
        request.setPlayerName(playerName);

        Player player = new Player(playerName);
        Game game = new Game();

        when(playerService.getPlayerByPlayerName(playerName)).thenReturn(Mono.just(player));
        when(gameService.createGame(player)).thenReturn(Mono.just(game));

        Mono<Game> responseMono = gameController.createGame(request);

        StepVerifier.create(responseMono)
                .expectNext(game)
                .verifyComplete();
    }
}
