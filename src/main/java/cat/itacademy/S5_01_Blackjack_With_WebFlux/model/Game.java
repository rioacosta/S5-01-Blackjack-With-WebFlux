package cat.itacademy.S5_01_Blackjack_With_WebFlux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Data
@Document("games")
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    private String id;
    private Long playerId;
    private List<Cards> playerCards = new ArrayList<>();
    private List<Cards> croupierCards = new ArrayList<>();
    private List<Cards> allCards = new ArrayList<>(); // Rastrea todas las cartas
    private String winner;
    private boolean finished;

    public Game(Long playerId) {
        this.playerId = playerId;
        this.finished = false;

        // Repartir cartas iniciales
        playerCards.add(drawCard());
        playerCards.add(drawCard());
        croupierCards.add(drawCard()); // Carta visible
        croupierCards.add(drawCard()); // Carta oculta
    }

    public Cards drawCard() {
        Cards newCard = Cards.generateCard(allCards);
        allCards.add(newCard);
        return newCard;
    }

    public int getPlayerScore() {
        return calculateScore(playerCards);
    }

    public int getCroupierScore() {
        return calculateScore(croupierCards);
    }

    private int calculateScore(List<Cards> cards) {
        int score = 0;
        int aceCount = 0;

        for (Cards card : cards) {
            if (card.getValue() == CardValueEnum.ACE) {
                score += 11;
                aceCount++;
            } else {
                score += card.getValue().getNumericValue();
            }
        }

        // Ajustar Ases de 11 a 1 si el total supera 21
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }


    public boolean isNaturalBlackjack(List<Cards> hand) {
        if (hand.size() != 2) return false;
        boolean hasAce = hand.stream().anyMatch(c -> c.getValue() == CardValueEnum.ACE);
        boolean hasTen = hand.stream().anyMatch(c ->
                c.getValue().getNumericValue() == 10 ||
                        c.getValue() == CardValueEnum.JACK ||
                        c.getValue() == CardValueEnum.QUEEN ||
                        c.getValue() == CardValueEnum.KING);
        return hasAce && hasTen;
    }

    public void addPlayerCards(Cards card) {
        playerCards.add(card);
    }

    public void addCroupierCards(Cards card) {
        croupierCards.add(card);
    }
}