package pizzaioli.production.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
import pizzaioli.production.infrastructure.adapters.out.persistence.repository.ProductJpaRepository;

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
}
