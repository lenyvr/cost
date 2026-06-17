package pizzaioli.production.infrastructure.mapper;

import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductTypeEntity;
import pizzaioli.production.infrastructure.dtos.request.CreateProductTypeRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.ProductTypeResponseDTO;

import java.util.Objects;

public class ProductTypeMapper {

    private ProductTypeMapper() {
    }

    public static ProductTypeEntity toEntity(ProductType productType) {
        if (Objects.isNull(productType)) {
            return null;
        }

        ProductTypeEntity entity = new ProductTypeEntity();
        entity.setId(productType.getId());
        entity.setName(productType.getName());
        entity.setActive(productType.isActive());
        entity.setCreatedDate(productType.getCreatedDate());

        return entity;
    }

    public static ProductType toDomainFromEntity(ProductTypeEntity productTypeEntity) {
        if (Objects.isNull(productTypeEntity)) {
            return null;
        }

        ProductType productType = new ProductType();
        productType.setId(productTypeEntity.getId());
        productType.setName(productTypeEntity.getName());
        if (Objects.nonNull(productTypeEntity.getActive())) {
            productType.setActive(productTypeEntity.getActive());
        }
        productType.setCreatedDate(productTypeEntity.getCreatedDate());

        return productType;
    }

    public static ProductType toDomainFromDTO(CreateProductTypeRequestDTO requestDTO){
        ProductType productType = new ProductType();
        productType.setName(requestDTO.name());
        productType.setActive(true);
        return  productType;
    }

    public static ProductTypeResponseDTO toDTOFromDomain(ProductType productType) {
        return new ProductTypeResponseDTO(productType.getName());
    }
}
