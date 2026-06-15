package pizzaioli.production.infrastructure.mapper;

import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.MeasurementUnitEntity;
import pizzaioli.production.infrastructure.dtos.request.CreateMeasurementUnitRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.MeasurementUnitResponseDTO;

import java.time.LocalDateTime;

public class MeasurementUnitMapper {
    public static MeasurementUnit toDomainFromCreateRequest(CreateMeasurementUnitRequestDTO dto) {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setActive(Boolean.TRUE);
        measurementUnit.setCode(dto.code());
        measurementUnit.setName(dto.name());
        measurementUnit.setCreatedDate(LocalDateTime.now());
        return measurementUnit;
    }

    public static MeasurementUnitResponseDTO toCreationResponseDTO(MeasurementUnit measurementUnit) {
        return new MeasurementUnitResponseDTO(
                measurementUnit.getCode(),
                measurementUnit.getName(),
                measurementUnit.isActive(),
                measurementUnit.getCreatedDate()
        );
    }

    public static MeasurementUnit toDomainFromEntity(MeasurementUnitEntity entity) {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setActive(entity.isActive());
        measurementUnit.setCode(entity.getCode());
        measurementUnit.setName(entity.getName());
        measurementUnit.setCreatedDate(entity.getCreatedDate());
        return measurementUnit;
    }

    public static MeasurementUnitEntity toEntity(MeasurementUnit measurementUnit) {
        return new MeasurementUnitEntity(
                measurementUnit.getCode(),
                measurementUnit.getName(),
                measurementUnit.isActive(),
                measurementUnit.getCreatedDate()
        );
    }
}
