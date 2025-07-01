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

    @Operation(summary = "List all players", description = "Get all players in the database")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("getAllPlayers")
    public Flux<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create player", description = "Add a new player to the database")
    public Mono<Player> createPlayer(@RequestBody CreatePlayerRequestDTO request) {
        return playerService.create(request.getUserName());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recover a player by ID", description = "Get a particular player by including their ID in the body")
    public Mono<Player> getPlayerById(@PathVariable int id) {
        return playerService.getPlayerById(id);
    }

    @PutMapping("/{playerId}")
    @Operation(summary = "Update a player's ", description = "Update a player data by including their ID in the body")
    public Mono<Player> updatePlayer(@RequestBody Player player, @PathVariable long id) {
        return playerService.updatePlayer(id, player.getUserName());
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete player", description = "Delete a player by ID")
    public Mono<Void> deletePlayer(@PathVariable int id) {
        return playerService.deletePlayer(id);
    }
}
