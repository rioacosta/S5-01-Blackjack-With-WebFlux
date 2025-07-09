package cat.itacademy.S5_01_Blackjack_With_WebFlux.controllers;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.RankingResponseDTO;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.services.RankingService;
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

    private final RankingService gameService;

    @GetMapping
    @Operation(summary = "Get players ranking")
    public Flux<RankingResponseDTO> ranking() {
        return gameService.getRanking();
    }
}
