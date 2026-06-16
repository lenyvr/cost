package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.DeleteMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.exceptions.MeasurementUnitNotFoundException;
import pizzaioli.production.domain.exceptions.MeasurementUnitHasDependenciesException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

import pizzaioli.production.domain.ports.output.ProductRepositorySPI;

import java.util.Objects;

public class DeleteMeasurementUnitUseCase implements DeleteMeasurementUnitUseCaseSPI {

    private final MeasurementUnitRepositorySPI measurementUnitRepositorySPI;
    private final ProductRepositorySPI productRepositorySPI;

    public DeleteMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI, 
                                        ProductRepositorySPI productRepositorySPI) {
        this.measurementUnitRepositorySPI = measurementUnitRepositorySPI;
        this.productRepositorySPI = productRepositorySPI;
    }

    @Override
    public void execute(String code) {
        MeasurementUnit measurementUnitFound = measurementUnitRepositorySPI.getByCode(code);
        
        if (Objects.isNull(measurementUnitFound) || !measurementUnitFound.isActive()) {
            throw new MeasurementUnitNotFoundException("Measurement unit with code '" + code + "' does not exist or is already inactive.");
        }

        if (productRepositorySPI.existsActiveProductByMeasurementUnitCode(code)) {
            throw new MeasurementUnitHasDependenciesException("No es posible eliminar el registro porque tiene dependencias.");
        }
        
        measurementUnitFound.setActive(false);
        measurementUnitRepositorySPI.save(measurementUnitFound);
    }
}
