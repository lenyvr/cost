package pizzaioli.production.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.exceptions.ValueRequiredException;
import pizzaioli.production.infrastructure.dtos.ErrorResponseDTO;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleProductTypeNotFoundException(RecordNotFoundException ex) {
        log.error("Record not found: {}",ex.getMessage(), ex);
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler({RecordHasDependenciesException.class
    , RecordAlreadyExistsException.class
    , ValueRequiredException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleConflictsException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponseDTO(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred");
    }
}
