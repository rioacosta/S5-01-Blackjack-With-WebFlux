package cat.itacademy.S5_01_Blackjack_With_WebFlux.controllers;


import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.RankingResponseDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.GameService;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final GameService gameService;
    private final PlayerService playerService;

    @GetMapping("/ranking")
    @Operation(summary = "Get players ranking")
    public Flux<RankingResponseDTO> ranking() {
        return gameService.getPlayersIDsOrderedByWins()
                .flatMap(rankingDTO ->
                        playerService.getPlayerById(rankingDTO.getUserId())
                                .map(player -> RankingResponseDTO.builder()
                                        .userId(rankingDTO.getUserId())
                                        .wins(rankingDTO.getWins())
                                        .userName(player.getUserName())
                                        .build()
                                )
                );
    }
}

