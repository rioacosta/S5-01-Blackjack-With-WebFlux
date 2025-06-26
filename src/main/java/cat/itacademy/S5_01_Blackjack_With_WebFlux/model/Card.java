package cat.itacademy.S5_01_Blackjack_With_WebFlux.model;


import lombok.Data;

import java.util.List;

@Data
public class Card {
    private CardSuitEnum suit;
    private String value;

    public static Card generateCard(List<Card> cardsAlreadySelected) {
        if (cardsAlreadySelected.size() >= 40) {
            // If all cards have been drawn throw an exception
            throw new IllegalStateException("All cards have been drawn");
        }
        Card card = new Card();
        int randomValue = (int) (Math.random() * 13) + 1;
        int randomSuit = (int) (Math.random() * 4) + 1;
        switch (randomSuit) {
            case 1:
                card.setSuit(CardSuitEnum.HEARTS);
                break;
            case 2:
                card.setSuit(CardSuitEnum.CLUBS);
                break;
            case 3:
                card.setSuit(CardSuitEnum.SPADES);
                break;
            case 4:
                card.setSuit(CardSuitEnum.DIAMONDS);
                break;
        }
        card.setValue(String.valueOf(randomValue));

        if (cardsAlreadySelected.contains(card)) {
            // If the card already exists, generate a new one
            return generateCard(cardsAlreadySelected);
        }
        return card;
    }
}