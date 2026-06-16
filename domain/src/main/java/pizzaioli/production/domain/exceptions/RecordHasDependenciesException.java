package pizzaioli.production.domain.exceptions;

public class RecordHasDependenciesException extends RuntimeException {
    public RecordHasDependenciesException(String message) {
        super(message);
    }
}
