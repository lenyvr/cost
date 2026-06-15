package pizzaioli.production.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.MeasurementUnitEntity;
import pizzaioli.production.infrastructure.adapters.out.persistence.repository.MeasurementUnitJpaRepository;
import pizzaioli.production.infrastructure.mapper.MeasurementUnitMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MeasurementUnitRepositoryAdapter implements MeasurementUnitRepositorySPI {

    private final MeasurementUnitJpaRepository jpaRepository;

    public MeasurementUnitRepositoryAdapter(MeasurementUnitJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public MeasurementUnit save(MeasurementUnit measurementUnit) {
        MeasurementUnitEntity savedEntity = jpaRepository.save(MeasurementUnitMapper.toEntity(measurementUnit));
        return new MeasurementUnit(
                savedEntity.getCode(),
                savedEntity.getName(),
                savedEntity.isActive(),
                savedEntity.getCreatedDate()
        );
    }

    @Override
    public MeasurementUnit getByCode(String code) {
        Optional<MeasurementUnitEntity> measurementUnitEntityFound = jpaRepository.findByCode(code);
        MeasurementUnit measurementUnit = null;
        if(measurementUnitEntityFound.isPresent()){
            measurementUnit = MeasurementUnitMapper.toDomainFromEntity(measurementUnitEntityFound.get());
        }
        return measurementUnit;
    }

    @Override
    public MeasurementUnit getByName(String name) {
        Optional<MeasurementUnitEntity> measurementUnitEntityFound = jpaRepository.findByName(name);
        MeasurementUnit measurementUnit = null;
        if(measurementUnitEntityFound.isPresent()){
            measurementUnit = MeasurementUnitMapper.toDomainFromEntity(measurementUnitEntityFound.get());
        }
        return measurementUnit;
    }
}
