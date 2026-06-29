package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.MeasurementUnitUseCaseSPI;
import pizzaioli.production.application.usecases.port.ProductCurrencyUseCaseSPI;
import pizzaioli.production.application.usecases.port.ProductTypeUseCaseSPI;
import pizzaioli.production.application.usecases.port.ProductUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.models.Product;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
import pizzaioli.production.domain.validation.ValidateEmptyField;

import java.util.Objects;

public class ProductUseCase implements ProductUseCaseSPI, ValidateEmptyField {

    private final ProductRepositorySPI productRepositorySPI;

    public ProductUseCase(ProductRepositorySPI productRepositorySPI) {
        this.productRepositorySPI = productRepositorySPI;
    }

    @Override
    public Product create(Product product) {
        Product found = productRepositorySPI.getByName(product.getName());
        verifyIfExists(found);
        product.setActive(true);
        if (Objects.nonNull(found)) {
            product.setId(found.getId());
            product.setCreatedDate(found.getCreatedDate());
        }
        return productRepositorySPI.save(product);
    }

    private void verifyIfExists(Product found) {
        if (Objects.nonNull(found) && found.isActive()) {
            throw new RecordAlreadyExistsException("Product with name '" + found.getName() + "' already exists.");
        }
    }
}
