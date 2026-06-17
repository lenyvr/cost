package pizzaioli.production.application.usecases.port;

import pizzaioli.production.domain.models.ProductCurrency;

public interface ProductCurrencyUseCaseSPI {
    ProductCurrency create(ProductCurrency productCurrency);
    void delete(String code);
}
