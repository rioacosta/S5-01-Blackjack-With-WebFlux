package cat.itacademy.S5_01_Blackjack_With_WebFlux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("games")
@RequiredArgsConstructor
public class Game {
    @Id
    private String id;
    private Long playerId;
    private List<Card> playerCards;
    private List<Card> croupierCards;
    private String winner;
    private boolean finished;

    public Game(Long playerId) {
        this.playerId = playerId;
        this.playerCards = new ArrayList<>();
        this.croupierCards = new ArrayList<>();
        // When the game starts, the player receives two cards and croupier receives one card
        playerCards.add(Card.generateCard(playerCards));
        playerCards.add(Card.generateCard(playerCards));
        croupierCards.add(Card.generateCard(playerCards));
        this.finished = false;
    }

    public void addPlayerCards(Card card) {
        if (playerCards == null) {
            playerCards = new ArrayList<>();
        }
        playerCards.add(card);
    }

    public void addCroupierCards(Card card) {
        if (croupierCards == null) {
            croupierCards = new ArrayList<>();
        }
        croupierCards.add(card);
    }

    public int getPlayerScore() {
        if (playerCards == null || playerCards.isEmpty()) {
            return 0; // Si no hay cartas, el puntaje es 0
        }
        return calculateScore(playerCards);
    }

    public int getCroupierScore() {
        return calculateScore(croupierCards);
    }

    private int calculateScore(List<Card> cards) {
        int total = 0;

        for (Card card : cards) {
            switch (card.getValue()) {
                case "1"://A
                    if (total + 11 > 21) {
                        total += 1; // Si sumar 11 excede 21, cuenta como 1
                    } else {
                        total += 11; // Asume que el As vale 11 inicialmente
                    }
                    break;
                case "11"://J
                case "12"://Q
                case "13"://K
                    total += 10; // Reyes, Reinas y Jotas valen 10
                    break;
                default:
                    total += Integer.parseInt(card.getValue());
            }
        }

        return total;
    }

}

