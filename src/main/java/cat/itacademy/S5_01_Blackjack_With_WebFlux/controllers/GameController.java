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
    public ResponseEntity<Mono<Game>> createGame(@RequestBody CreateGameRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.getPlayerByPlayerName(request.getPlayerName())
                .flatMap(player -> gameService.createGame(player)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of an already played Blackjack game", description = "ID required")
    public ResponseEntity<Mono<Game>> getGame(@PathVariable String id) {
        return ResponseEntity.ok(gameService.getGame(id));
    }

    @PostMapping("/{gameId}/play")
    @Operation(summary = "Play the created Blackjack game")
    public ResponseEntity<Mono<Game>> play(@PathVariable String gameId, @RequestBody PlayRequestDTO request) {
        return ResponseEntity.ok(gameService.play(gameId, request.isAskForCard()));
    }

    @GetMapping()
    @Operation(summary = "Get all Blackjack games played", description = "Get all games played in this session")
    public Flux<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete game", description = "delete a game whit game ID")
    public ResponseEntity<Mono<Void>> delete(@PathVariable String id) {
        gameService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
