package cat.itacademy.S5_01_Blackjack_With_WebFlux.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankingResponseDTO {
    String userName;
    Long userId;
    Long wins;
}
