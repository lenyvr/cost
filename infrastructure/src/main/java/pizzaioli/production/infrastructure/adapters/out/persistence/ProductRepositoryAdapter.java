package pizzaioli.production.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;
import pizzaioli.production.domain.models.Product;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductEntity;
import pizzaioli.production.infrastructure.adapters.out.persistence.repository.ProductJpaRepository;
import pizzaioli.production.infrastructure.mapper.ProductMapper;

import java.util.Optional;

@Component
public class ProductRepositoryAdapter implements ProductRepositorySPI {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsActiveProductByMeasurementUnitCode(String measurementUnitCode) {
        return jpaRepository.existsByMeasurementUnitCodeAndActiveTrue(measurementUnitCode);
    }

    @Override
    public boolean existsActiveProductByProductTypeId(Integer productTypeId) {
        return jpaRepository.existsByProductTypeIdAndActiveTrue(productTypeId);
    }

    @Override
    public boolean existsActiveProductByProductCurrencyId(Integer productCurrencyId) {
        return jpaRepository.existsByProductCurrencyIdAndActiveTrue(productCurrencyId);
    }

    @Override
    public Product save(Product product) {
        ProductEntity savedEntity = jpaRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomainFromEntity(savedEntity);
    }

    @Override
    public Product getByName(String name) {
        Optional<ProductEntity> entityFound = jpaRepository.findByName(name);
        return entityFound.map(ProductMapper::toDomainFromEntity).orElse(null);
    }
}

