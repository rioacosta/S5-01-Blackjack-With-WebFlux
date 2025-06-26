package cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
