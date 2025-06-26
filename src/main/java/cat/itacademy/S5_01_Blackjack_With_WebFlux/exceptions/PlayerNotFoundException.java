package cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
