package pizzaioli.production.infrastructure.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductEntity;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Integer> {
    
    boolean existsByMeasurementUnitCodeAndActiveTrue(String measurementUnitCode);
}
