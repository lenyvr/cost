package pizzaioli.production.infrastructure.dtos;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(HttpStatus code, String message) {
}
