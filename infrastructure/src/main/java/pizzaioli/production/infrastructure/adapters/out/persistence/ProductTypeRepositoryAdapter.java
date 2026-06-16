package pizzaioli.production.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.domain.ports.output.ProductTypeRepositorySPI;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductTypeEntity;
import pizzaioli.production.infrastructure.adapters.out.persistence.repository.ProductTypeJpaRepository;
import pizzaioli.production.infrastructure.mapper.ProductTypeMapper;

import java.util.Optional;

@Component
public class ProductTypeRepositoryAdapter implements ProductTypeRepositorySPI {

    private final ProductTypeJpaRepository jpaRepository;

    public ProductTypeRepositoryAdapter(ProductTypeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ProductType save(ProductType productType) {
        ProductTypeEntity savedEntity = jpaRepository.save(ProductTypeMapper.toEntity(productType));
        return ProductTypeMapper.toDomainFromEntity(savedEntity);
    }

    @Override
    public ProductType getByName(String name) {
        Optional<ProductTypeEntity> productTypeEntityFound = jpaRepository.findByName(name);
        return productTypeEntityFound.map(ProductTypeMapper::toDomainFromEntity).orElse(null);
    }
}
