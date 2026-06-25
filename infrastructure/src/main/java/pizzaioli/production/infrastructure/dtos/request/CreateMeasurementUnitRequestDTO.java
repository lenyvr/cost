package pizzaioli.production.infrastructure.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMeasurementUnitRequestDTO(
    @NotBlank(message = "Code is required")
    @Size(min = 1, max = 10, message = "Length code must be between 1 and 10 characters")
    String code,
    @NotBlank(message = "Code is required")
    @Size(min = 1, max = 100, message = "Length code must be between 1 and 10 characters")
    String name
) {}
