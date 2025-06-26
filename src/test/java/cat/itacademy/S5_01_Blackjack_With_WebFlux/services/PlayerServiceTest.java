package cat.itacademy.S5_01_Blackjack_With_WebFlux.services;

import cat.itacademy.S5_01_Blackjack_With_WebFlux.model.Player;
import cat.itacademy.S5_01_Blackjack_With_WebFlux.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepo;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        playerRepo = mock(PlayerRepository.class);
        playerService = new PlayerService(playerRepo);
    }

    @Test
    void shouldCreatePlayerWithSameName() {
        String userName = "Alice";
        Player player = new Player(userName);

        when(playerRepo.findByUserName(userName)).thenReturn(Mono.empty());
        when(playerRepo.save(ArgumentMatchers.any(Player.class))).thenReturn(Mono.just(player));

        Mono<Player> result = playerService.create("Alice");

        StepVerifier.create(result)
                .expectNextMatches(p -> p.getUserName().equals("Alice"))
                .verifyComplete();
    }

}

