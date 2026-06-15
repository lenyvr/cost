package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.CreateMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.exceptions.MeasurementUnitAlreadyExistsException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

import java.util.Objects;

public class CreateMeasurementUnitUseCase implements CreateMeasurementUnitUseCaseSPI {

    private final MeasurementUnitRepositorySPI measurementUnitRepositorySPI;

    public CreateMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI) {
        this.measurementUnitRepositorySPI = measurementUnitRepositorySPI;
    }

    public MeasurementUnit execute(MeasurementUnit measurementUnit) {
        MeasurementUnit measurementUnitFound = verifyExistence(measurementUnit);
        MeasurementUnit measurementUnitSaved=null;
        if(Objects.nonNull(measurementUnitFound)){
            measurementUnitFound.setActive(Boolean.TRUE);
            measurementUnitFound.setName(measurementUnit.getName());
            measurementUnitSaved = measurementUnitRepositorySPI.save(measurementUnitFound);
        }else {
            measurementUnitSaved = measurementUnitRepositorySPI.save(measurementUnit);
        }
        return  measurementUnitSaved;
    }

    private MeasurementUnit verifyExistence(MeasurementUnit measurementUnit) {
        MeasurementUnit measurementUnitFound = measurementUnitRepositorySPI.getByCode(measurementUnit.getCode());
        if (Objects.nonNull(measurementUnitFound) && measurementUnitFound.isActive()) {
            throw new MeasurementUnitAlreadyExistsException("Measurement Unit with code '" + measurementUnit.getCode()
                    + "' already exists.");
        }

        measurementUnitFound = measurementUnitRepositorySPI.getByName(measurementUnit.getName());
        if (Objects.nonNull(measurementUnitFound) && measurementUnitFound.isActive()) {
            throw new MeasurementUnitAlreadyExistsException("Measurement Unit with name '" + measurementUnit.getName()
                    + "' already exists.");
        }
        return measurementUnitFound;
    }
}
