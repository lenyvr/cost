package pizzaioli.production.domain.exceptions;

public class MeasurementUnitNotFoundException extends RuntimeException {
    public MeasurementUnitNotFoundException(String message) {
        super(message);
    }
}
