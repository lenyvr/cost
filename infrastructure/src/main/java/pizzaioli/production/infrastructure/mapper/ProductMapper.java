package pizzaioli.production.infrastructure.mapper;

import pizzaioli.production.domain.models.Product;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductEntity;
import pizzaioli.production.infrastructure.dtos.request.CreateProductRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.ProductResponseDTO;

import java.util.Objects;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductEntity toEntity(Product product) {
        if (Objects.isNull(product)) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setAmountValue(product.getAmountValue());
        entity.setMeasurementUnitCode(product.getMeasurementUnitCode());
        entity.setProductCurrencyId(product.getProductCurrencyId());
        entity.setProductTypeId(product.getProductTypeId());
        entity.setActive(product.isActive());
        entity.setCreatedDate(product.getCreatedDate());

        return entity;
    }

    public static Product toDomainFromEntity(ProductEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setAmountValue(entity.getAmountValue());
        product.setMeasurementUnitCode(entity.getMeasurementUnitCode());
        product.setProductCurrencyId(entity.getProductCurrencyId());
        product.setProductTypeId(entity.getProductTypeId());
        if (Objects.nonNull(entity.getActive())) {
            product.setActive(entity.getActive());
        }
        product.setCreatedDate(entity.getCreatedDate());

        return product;
    }

    public static Product toDomainFromDTO(CreateProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setName(requestDTO.name());
        product.setAmountValue(requestDTO.amountValue());
        product.setMeasurementUnitCode(requestDTO.measurementUnitCode());
        product.setProductCurrencyId(requestDTO.productCurrencyId());
        product.setProductTypeId(requestDTO.productTypeId());
        product.setActive(true);
        return product;
    }

    public static ProductResponseDTO toDTOFromDomain(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getAmountValue(),
                product.getMeasurementUnitCode(),
                product.getProductCurrencyId(),
                product.getProductTypeId(),
                product.isActive(),
                product.getCreatedDate()
        );
    }
}
