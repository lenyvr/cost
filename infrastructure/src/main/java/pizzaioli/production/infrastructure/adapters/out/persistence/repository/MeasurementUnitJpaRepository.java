package pizzaioli.production.infrastructure.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.MeasurementUnitEntity;

import java.util.List;
import java.util.Optional;

public interface MeasurementUnitJpaRepository extends JpaRepository<MeasurementUnitEntity, String> {

    Optional<MeasurementUnitEntity> findByCode(String code);
    Optional<MeasurementUnitEntity> findByName(String name);
}
