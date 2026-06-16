package pizzaioli.production.domain.exceptions;

public class MeasurementUnitHasDependenciesException extends RuntimeException {
    public MeasurementUnitHasDependenciesException(String message) {
        super(message);
    }
}
