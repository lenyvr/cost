package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.DeleteProductTypeUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
import pizzaioli.production.domain.ports.output.ProductTypeRepositorySPI;

import java.util.Objects;

public class DeleteProductTypeUseCase implements DeleteProductTypeUseCaseSPI {

    private final ProductTypeRepositorySPI productTypeRepositorySPI;
    private final ProductRepositorySPI productRepositorySPI;

    public DeleteProductTypeUseCase(ProductTypeRepositorySPI productTypeRepositorySPI, ProductRepositorySPI productRepositorySPI) {
        this.productTypeRepositorySPI = productTypeRepositorySPI;
        this.productRepositorySPI = productRepositorySPI;
    }

    @Override
    public void execute(String name) {
        ProductType found = productTypeRepositorySPI.getByName(name);

        if (Objects.isNull(found) || !found.isActive()) {
            throw new RecordNotFoundException("Product type with name '" + name + "' does not exist or is already inactive.");
        }

        if (productRepositorySPI.existsActiveProductByProductTypeId(found.getId())) {
            throw new RecordHasDependenciesException("No es posible eliminar el registro porque tiene dependencias.");
        }

        found.setActive(false);
        productTypeRepositorySPI.save(found);
    }
}
