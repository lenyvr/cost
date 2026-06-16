package pizzaioli.production.infrastructure.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductTypeEntity;

import java.util.Optional;

public interface ProductTypeJpaRepository extends JpaRepository<ProductTypeEntity, Integer> {
    Optional<ProductTypeEntity> findByName(String name);
}
