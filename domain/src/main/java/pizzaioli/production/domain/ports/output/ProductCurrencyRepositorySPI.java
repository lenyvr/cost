package pizzaioli.production.domain.ports.output;

import pizzaioli.production.domain.models.ProductCurrency;

public interface ProductCurrencyRepositorySPI {
    ProductCurrency save(ProductCurrency productCurrency);
    ProductCurrency getByCode(String code);
}
