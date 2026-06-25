package pizzaioli.production.infrastructure.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductTypeRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(min=1, max = 100, message = "Name must not exceed 100 characters")
        String name
) {}
