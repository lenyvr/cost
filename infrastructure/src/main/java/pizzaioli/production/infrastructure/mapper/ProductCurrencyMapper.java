package pizzaioli.production.infrastructure.mapper;

import pizzaioli.production.domain.models.ProductCurrency;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductCurrencyEntity;
import pizzaioli.production.infrastructure.dtos.request.CreateProductCurrencyRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.ProductCurrencyResponseDTO;

import java.util.Objects;

public class ProductCurrencyMapper {

    private ProductCurrencyMapper() {
    }

    public static ProductCurrencyEntity toEntity(ProductCurrency productCurrency) {
        if (Objects.isNull(productCurrency)) {
            return null;
        }

        ProductCurrencyEntity entity = new ProductCurrencyEntity();
        entity.setId(productCurrency.getId());
        entity.setName(productCurrency.getName());
        entity.setSymbol(productCurrency.getSymbol());
        entity.setCode(productCurrency.getCode());
        entity.setDescription(productCurrency.getDescription());
        entity.setActive(productCurrency.isActive());
        entity.setCreatedDate(productCurrency.getCreatedDate());

        return entity;
    }

    public static ProductCurrency toDomainFromEntity(ProductCurrencyEntity productCurrencyEntity) {
        if (Objects.isNull(productCurrencyEntity)) {
            return null;
        }

        ProductCurrency productCurrency = new ProductCurrency();
        productCurrency.setId(productCurrencyEntity.getId());
        productCurrency.setName(productCurrencyEntity.getName());
        productCurrency.setSymbol(productCurrencyEntity.getSymbol());
        productCurrency.setCode(productCurrencyEntity.getCode());
        productCurrency.setDescription(productCurrencyEntity.getDescription());
        if (Objects.nonNull(productCurrencyEntity.getActive())) {
            productCurrency.setActive(productCurrencyEntity.getActive());
        }
        productCurrency.setCreatedDate(productCurrencyEntity.getCreatedDate());

        return productCurrency;
    }

    public static ProductCurrency toDomainFromDTO(CreateProductCurrencyRequestDTO requestDTO) {
        ProductCurrency productCurrency = new ProductCurrency();
        productCurrency.setName(requestDTO.name());
        productCurrency.setSymbol(requestDTO.symbol());
        productCurrency.setCode(requestDTO.code());
        productCurrency.setDescription(requestDTO.description());
        return productCurrency;
    }

    public static ProductCurrencyResponseDTO toDTOFromDomain(ProductCurrency productCurrency) {
       return  new ProductCurrencyResponseDTO(
                productCurrency.getName(),
                productCurrency.getSymbol(),
                productCurrency.getCode(),
                productCurrency.getDescription()
        );
    }
}
