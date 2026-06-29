package pizzaioli.production.infrastructure.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductEntity;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByMeasurementUnitCodeAndActiveTrue(String measurementUnitCode);
    boolean existsByProductTypeIdAndActiveTrue(Integer productTypeId);
    boolean existsByProductCurrencyIdAndActiveTrue(Integer productCurrencyId);
    Optional<ProductEntity> findByName(String name);
}

