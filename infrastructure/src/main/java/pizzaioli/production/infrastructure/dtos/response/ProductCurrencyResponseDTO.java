package pizzaioli.production.infrastructure.dtos.response;

public record ProductCurrencyResponseDTO(
        String name,
        String symbol,
        String code,
        String description
) {
}
