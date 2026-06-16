package pizzaioli.production.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pizzaioli.production.domain.exceptions.MeasurementUnitAlreadyExistsException;
import pizzaioli.production.domain.exceptions.MeasurementUnitNotFoundException;
import pizzaioli.production.infrastructure.dtos.ErrorResponseDTO;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MeasurementUnitAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleMeasurementUnitAlreadyExistsException(MeasurementUnitAlreadyExistsException ex) {
        log.error("Measurement Unit already exists: {}",ex.getMessage(), ex);
        return new ErrorResponseDTO(HttpStatus.CONFLICT,"The measurement unit already exists");
    }

    @ExceptionHandler(MeasurementUnitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleMeasurementUnitNotFoundException(MeasurementUnitNotFoundException ex) {
        log.error("Measurement Unit not found: {}",ex.getMessage(), ex);
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND,"The measurement unit does not exist");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred");
    }
}
