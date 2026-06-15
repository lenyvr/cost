package pizzaioli.production.domain.exceptions;

public class ValueRequiredException extends RuntimeException {
    public ValueRequiredException(String message) {
        super(message);
    }
}
