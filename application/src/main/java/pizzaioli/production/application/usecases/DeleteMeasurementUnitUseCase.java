package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.DeleteMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.exceptions.MeasurementUnitNotFoundException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

import java.util.Objects;

public class DeleteMeasurementUnitUseCase implements DeleteMeasurementUnitUseCaseSPI {

    private final MeasurementUnitRepositorySPI measurementUnitRepositorySPI;

    public DeleteMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI) {
        this.measurementUnitRepositorySPI = measurementUnitRepositorySPI;
    }

    @Override
    public void execute(String code) {
        MeasurementUnit measurementUnitFound = measurementUnitRepositorySPI.getByCode(code);
        
        if (Objects.isNull(measurementUnitFound) || !measurementUnitFound.isActive()) {
            throw new MeasurementUnitNotFoundException("Measurement unit with code '" + code + "' does not exist or is already inactive.");
        }
        
        measurementUnitFound.setActive(false);
        measurementUnitRepositorySPI.save(measurementUnitFound);
    }
}
