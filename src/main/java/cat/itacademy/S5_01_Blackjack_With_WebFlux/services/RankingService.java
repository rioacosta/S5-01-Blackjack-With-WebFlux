package cat.itacademy.S5_01_Blackjack_With_WebFlux.services;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.dto.RankingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final GameService gameService;
    private final PlayerService playerService;

    public Flux<RankingResponseDTO> getRanking() {
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


