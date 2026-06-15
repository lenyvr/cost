package pizzaioli.production.infrastructure.dtos.request;

public record CreateMeasurementUnitRequestDTO(
    String code,
    String name
) {}
