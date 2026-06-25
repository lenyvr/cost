package pizzaioli.production.application.usecases.port;

import pizzaioli.production.domain.models.MeasurementUnit;

public interface MeasurementUnitUseCaseSPI {

    MeasurementUnit create(MeasurementUnit measurementUnit);
    void delete(String code);
}
