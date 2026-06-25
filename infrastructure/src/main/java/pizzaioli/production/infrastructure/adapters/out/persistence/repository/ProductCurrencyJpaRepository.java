package pizzaioli.production.infrastructure.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductCurrencyEntity;

import java.util.Optional;

public interface ProductCurrencyJpaRepository extends JpaRepository<ProductCurrencyEntity, Integer> {
    Optional<ProductCurrencyEntity> findByCode(String code);
}
