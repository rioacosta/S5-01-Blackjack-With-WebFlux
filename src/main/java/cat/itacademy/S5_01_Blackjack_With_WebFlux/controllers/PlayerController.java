package cat.itacademy.S5_01_Blackjack_With_WebFlux.controllers;


import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.CreatePlayerRequestDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Player;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    @Operation(summary = "Lista todos los jugadores", description = "Obtiene todos los jugadores registrados en la base de datos")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public Flux<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PostMapping
    @Operation(summary = "Create player")
    public ResponseEntity<Mono<Player>> createPlayer(@RequestBody CreatePlayerRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.create(request.getUserName()));
    }

    @GetMapping("/getAll")
    @Operation(summary = "Recover all players")
    public ResponseEntity<Flux<Player>> getAll() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recover a player by ID")
    public Mono<Player> getPlayerById(@PathVariable int id) {
        return playerService.getPlayerById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a player's data by including their ID in the body")
    public ResponseEntity<Mono<Player>> updatePlayer(@RequestBody Player player, @PathVariable long id) {
        return ResponseEntity.ok(playerService.updatePlayer(id, player.getUserName()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a player by ID")
    public Mono<Void> deletePlayer(@PathVariable int id) {
        return playerService.deletePlayer(id);
    }
}
