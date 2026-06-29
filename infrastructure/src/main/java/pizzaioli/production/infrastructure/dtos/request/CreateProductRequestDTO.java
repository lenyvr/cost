package pizzaioli.production.infrastructure.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateProductRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 1, max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotNull(message = "Amount value is required")
        @Positive(message = "Amount value must be greater than zero")
        Double amountValue,

        @NotBlank(message = "Measurement unit code is required")
        @Size(min = 1, max = 10, message = "Measurement unit code must not exceed 10 characters")
        String measurementUnitCode,

        @NotNull(message = "Product currency id is required")
        Long productCurrencyId,

        @NotNull(message = "Product type id is required")
        Long productTypeId
) {}
