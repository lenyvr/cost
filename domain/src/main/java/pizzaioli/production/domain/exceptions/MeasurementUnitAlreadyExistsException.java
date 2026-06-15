package pizzaioli.production.domain.exceptions;

public class MeasurementUnitAlreadyExistsException extends RuntimeException {
    public MeasurementUnitAlreadyExistsException(String message) {
        super(message);
    }
}
