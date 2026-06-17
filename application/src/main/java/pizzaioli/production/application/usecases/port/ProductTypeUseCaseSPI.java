package pizzaioli.production.application.usecases.port;

import pizzaioli.production.domain.models.ProductType;

public interface ProductTypeUseCaseSPI {
    ProductType create(ProductType productType);
    void delete(String name);
}
