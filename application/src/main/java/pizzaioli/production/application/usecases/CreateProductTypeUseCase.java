package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.CreateProductTypeUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.domain.ports.output.ProductTypeRepositorySPI;

import java.util.Objects;

public class CreateProductTypeUseCase implements CreateProductTypeUseCaseSPI {

    private final ProductTypeRepositorySPI productTypeRepositorySPI;

    public CreateProductTypeUseCase(ProductTypeRepositorySPI productTypeRepositorySPI) {
        this.productTypeRepositorySPI = productTypeRepositorySPI;
    }

    @Override
    public ProductType execute(ProductType productType) {
        ProductType found = productTypeRepositorySPI.getByName(productType.getName());
        
        if (Objects.nonNull(found)) {
            if (found.isActive()) {
                throw new RecordAlreadyExistsException("Product type with name '" + productType.getName() + "' already exists.");
            } else {
                found.setActive(true);
                return productTypeRepositorySPI.save(found);
            }
        }
        
        return productTypeRepositorySPI.save(productType);
    }
}
