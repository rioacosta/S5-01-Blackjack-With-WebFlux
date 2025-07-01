package cat.itacademy.S5_01_Blackjack_With_WebFlux.model;

import lombok.Getter;

@Getter
public enum CardValueEnum {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGTH("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10);

    private final String symbol;
    private final int numericValue; // Múltiples valores para el As

    CardValueEnum(String symbol, int numericValue) {
        this.symbol = symbol;
        this.numericValue = numericValue;
    }

}