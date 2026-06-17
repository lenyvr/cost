package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.MeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
import pizzaioli.production.domain.validation.ValidateEmptyField;

import java.util.Objects;

public class MeasurementUnitUseCase implements MeasurementUnitUseCaseSPI, ValidateEmptyField {

    private final MeasurementUnitRepositorySPI measurementUnitRepositorySPI;
    private final ProductRepositorySPI productRepositorySPI;

    public MeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI
    ,  ProductRepositorySPI productRepositorySPI) {
        this.measurementUnitRepositorySPI = measurementUnitRepositorySPI;
        this.productRepositorySPI = productRepositorySPI;
    }

    public MeasurementUnit create(MeasurementUnit measurementUnit) {
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

    @Override
    public void delete(String code) {
        validateEmptyField(code,"code");
        MeasurementUnit measurementUnitFound = measurementUnitRepositorySPI.getByCode(code);
        verifyIfPossibleToDelete(measurementUnitFound,code);
        measurementUnitFound.setActive(false);
        measurementUnitRepositorySPI.save(measurementUnitFound);
    }

    private MeasurementUnit verifyExistence(MeasurementUnit measurementUnit) {
        MeasurementUnit measurementUnitFoundByCode = measurementUnitRepositorySPI.getByCode(measurementUnit.getCode());
        if (Objects.nonNull(measurementUnitFoundByCode) && measurementUnitFoundByCode.isActive()) {
            throw new RecordAlreadyExistsException("Measurement Unit with code '" + measurementUnit.getCode()
                    + "' already exists.");
        }

        MeasurementUnit measurementUnitFoundByName = measurementUnitRepositorySPI.getByName(measurementUnit.getName());
        if (Objects.nonNull(measurementUnitFoundByName) && measurementUnitFoundByName.isActive()) {
            throw new RecordAlreadyExistsException("Measurement Unit with name '" + measurementUnit.getName()
                    + "' already exists.");
        }
        
        return Objects.nonNull(measurementUnitFoundByCode) ? measurementUnitFoundByCode : measurementUnitFoundByName;
    }

    private void verifyIfPossibleToDelete( MeasurementUnit measurementUnit, String code){
        if (Objects.isNull(measurementUnit) || !measurementUnit.isActive()) {
            throw new RecordNotFoundException("Measurement unit with code '" + code + "' does not exist or is already inactive.");
        }

        if (productRepositorySPI.existsActiveProductByMeasurementUnitCode(code)) {
            throw new RecordHasDependenciesException("No es posible eliminar el registro porque tiene dependencias.");
        }
    }
}
