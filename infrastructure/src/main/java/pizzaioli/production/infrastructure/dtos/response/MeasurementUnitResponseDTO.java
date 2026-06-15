package pizzaioli.production.infrastructure.dtos.response;

import java.time.LocalDateTime;

public record MeasurementUnitResponseDTO(
    String code,
    String name,
    boolean active,
    LocalDateTime createdDate
) {}
