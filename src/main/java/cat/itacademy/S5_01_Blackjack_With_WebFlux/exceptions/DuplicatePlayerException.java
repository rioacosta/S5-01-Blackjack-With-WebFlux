package cat.itacademy.S5_01_Blackjack_With_WebFlux.exceptions;

public class DuplicatePlayerException extends RuntimeException {
    public DuplicatePlayerException(String message) {
        super(message);
    }
}

