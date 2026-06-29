package pizzaioli.production.infrastructure.dtos.response;

import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        Double amountValue,
        String measurementUnitCode,
        Long productCurrencyId,
        Long productTypeId,
        boolean active,
        LocalDateTime createdDate
) {}
