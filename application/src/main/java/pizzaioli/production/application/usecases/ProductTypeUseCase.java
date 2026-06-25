package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.ProductTypeUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.models.ProductCurrency;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
import pizzaioli.production.domain.ports.output.ProductTypeRepositorySPI;
import pizzaioli.production.domain.validation.ValidateEmptyField;

import java.util.Objects;

public class ProductTypeUseCase implements ProductTypeUseCaseSPI, ValidateEmptyField {

    private final ProductTypeRepositorySPI productTypeRepositorySPI;
    private final ProductRepositorySPI productRepositorySPI;

    public ProductTypeUseCase(ProductTypeRepositorySPI productTypeRepositorySPI
    , ProductRepositorySPI productRepositorySPI) {
        this.productTypeRepositorySPI = productTypeRepositorySPI;
        this.productRepositorySPI = productRepositorySPI;
    }

    @Override
    public ProductType create(ProductType productType) {
        ProductType found = productTypeRepositorySPI.getByName(productType.getName());
        verifyIfExists(found);
        productType.setActive(true);
        if (Objects.nonNull(found)) {
          productType.setId(found.getId());
        }
        
        return productTypeRepositorySPI.save(productType);
    }

    @Override
    public void delete(String name) {
        validateEmptyField(name, "name");
        ProductType found = productTypeRepositorySPI.getByName(name);
        verifyIfPossibleToDelete(found, name);
        found.setActive(false);
        productTypeRepositorySPI.save(found);
    }

    private void verifyIfExists(ProductType found) {
        if (Objects.nonNull(found) && found.isActive()) {
            throw new RecordAlreadyExistsException("Product type with name '" + found.getName() + "' already exists.");
        }
    }

    private void verifyIfPossibleToDelete(ProductType productType, String name){
        if (Objects.isNull(productType) || !productType.isActive()) {
            throw new RecordNotFoundException("Product type with name '" + name + "' does not exist or is already inactive.");
        }

        if (productRepositorySPI.existsActiveProductByProductTypeId(productType.getId())) {
            throw new RecordHasDependenciesException("No es posible eliminar el registro porque tiene dependencias.");
        }
    }
}
