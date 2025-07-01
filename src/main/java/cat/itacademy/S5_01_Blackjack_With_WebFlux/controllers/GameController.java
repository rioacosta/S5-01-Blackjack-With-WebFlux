package cat.itacademy.S5_01_Blackjack_With_WebFlux.controllers;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.CreateGameRequestDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.PlayRequestDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Game;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.GameService;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Tag(name = "Game", description = "Blackjack's endpoints ")
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;

    @PostMapping("/new")
    @Operation(summary = "Create a new Blackjack game")
    public Mono<Game> createGame(@RequestBody CreateGameRequestDTO request) {
        return playerService.getPlayerByPlayerName(request.getPlayerName())
                .flatMap(player -> gameService.createGame(player));
        // Status 201 CREATED puede manejarse con un filtro global o personalizado si lo deseas.
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of an already played Blackjack game", description = "ID required")
    public Mono<Game> getGame(@PathVariable String id) {
        return gameService.getGame(id);
    }

    @PostMapping("/{gameId}/play")
    @Operation(summary = "Play the created Blackjack game")
    public Mono<Game> play(@PathVariable String gameId, @RequestBody PlayRequestDTO request) {
        return gameService.play(gameId, request.isAskForCard());
    }

    @GetMapping()
    @Operation(summary = "Get all Blackjack games played", description = "Get all games played in this session")
    public Flux<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a game by ID")
    public Mono<Void> delete(@PathVariable String id) {
        return gameService.delete(id);
        // HTTP 204 NO CONTENT se devuelve automáticamente cuando el Mono<Void> termina sin errores.
    }
}
