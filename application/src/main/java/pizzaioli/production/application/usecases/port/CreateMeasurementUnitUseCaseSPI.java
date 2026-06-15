package pizzaioli.production.application.usecases.port;

import pizzaioli.production.domain.models.MeasurementUnit;

public interface CreateMeasurementUnitUseCaseSPI {

    MeasurementUnit execute(MeasurementUnit measurementUnit);
}
