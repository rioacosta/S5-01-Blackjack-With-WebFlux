package cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions;

public class GameAlreadyFinishedException extends RuntimeException {
    public GameAlreadyFinishedException(String message) {
        super(message);
    }
}
