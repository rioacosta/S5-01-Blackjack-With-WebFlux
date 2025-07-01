package cat.itacademy.S5_01_Blackjack_With_WebFlux.model;

import lombok.Data;
import lombok.Getter;

import java.util.*;

@Data
@Getter
public class Cards {
    private CardSuitEnum suit;
    private CardValueEnum value; // Usar enum para valores
    private static final Random RANDOM = new Random();

    // Generación eficiente sin recursión
    public static Cards generateCard(List<Cards> usedCards) {
        List<Cards> availableCards = new ArrayList<>(createFullDeck());
        availableCards.removeAll(usedCards);

        if (availableCards.isEmpty()) {
            throw new IllegalStateException("No cards left in deck");
        }

        return availableCards.get(RANDOM.nextInt(availableCards.size()));
    }

    private static List<Cards> createFullDeck() {
        List<Cards> deck = new ArrayList<>();
        for (CardSuitEnum suit : CardSuitEnum.values()) {
            for (CardValueEnum value : CardValueEnum.values()) {
                Cards card = new Cards();
                card.setSuit(suit);
                card.setValue(value);
                deck.add(card);
            }
        }
        return deck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cards card = (Cards) o;
        return suit == card.suit && value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }
}