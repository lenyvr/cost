package pizzaioli.production.domain.ports.output;

import pizzaioli.production.domain.models.MeasurementUnit;

public interface MeasurementUnitRepositorySPI {
    MeasurementUnit save(MeasurementUnit measurementUnit);
    MeasurementUnit getByCode(String code);
    MeasurementUnit getByName(String name);
}
