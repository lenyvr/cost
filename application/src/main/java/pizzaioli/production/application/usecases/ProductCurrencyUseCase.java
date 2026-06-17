package pizzaioli.production.application.usecases;

import pizzaioli.production.application.usecases.port.ProductCurrencyUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.models.ProductCurrency;
import pizzaioli.production.domain.ports.output.ProductCurrencyRepositorySPI;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
import pizzaioli.production.domain.validation.ValidateEmptyField;

import java.util.Objects;

public class ProductCurrencyUseCase implements ProductCurrencyUseCaseSPI, ValidateEmptyField {

    private final ProductCurrencyRepositorySPI productCurrencyRepositorySPI;
    private final ProductRepositorySPI productRepositorySPI;

    public ProductCurrencyUseCase(ProductCurrencyRepositorySPI productCurrencyRepositorySPI
    , ProductRepositorySPI productRepositorySPI) {
        this.productCurrencyRepositorySPI = productCurrencyRepositorySPI;
        this.productRepositorySPI = productRepositorySPI;
    }

    @Override
    public ProductCurrency create(ProductCurrency productCurrency) {
        ProductCurrency found = productCurrencyRepositorySPI.getByCode(productCurrency.getCode());
        verifyIfExists(found);

        productCurrency.setActive(Boolean.TRUE);
        if (Objects.nonNull(found)) {
            productCurrency.setId(found.getId());
            productCurrency.setCreatedDate(found.getCreatedDate());
        }
        return productCurrencyRepositorySPI.save(productCurrency);
    }

    @Override
    public void delete(String code) {
        validateEmptyField(code, "code");
        ProductCurrency found = productCurrencyRepositorySPI.getByCode(code);
        verifyIfIsPossibleDelete(found, code);
        found.setActive(false);
        productCurrencyRepositorySPI.save(found);
    }

    private void verifyIfIsPossibleDelete(ProductCurrency found, String code) {
        if (Objects.isNull(found) || !found.isActive()) {
            throw new RecordNotFoundException("Product currency with code '" + code + "' does not exist or is already inactive.");
        }

        if (productRepositorySPI.existsActiveProductByProductCurrencyId(found.getId())) {
            throw new RecordHasDependenciesException("No es posible eliminar el registro porque tiene dependencias.");
        }
    }

    private void verifyIfExists(ProductCurrency found) {
        if (Objects.nonNull(found) && found.isActive()) {
            throw new RecordAlreadyExistsException("Product currency with code '" + found.getCode() + "' already exists.");
        }
    }
}
