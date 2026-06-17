package pizzaioli.production.infrastructure.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductCurrencyRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 1, max = 100, message = "Length name must be between 1 and 100 characters")
        String name,
        @NotBlank(message = "Symbol is required")
        @Size(min = 1, max = 5, message = "Length symbol must be between 1 and 5 characters")
        String symbol,
        @NotBlank(message = "Code is required")
        @Size(min = 1, max = 10, message = "Length code must be between 1 and 10 characters")
        String code,
        String description
) {
}
