package pizzaioli.production.application.usecases.port;

import pizzaioli.production.domain.models.ProductType;

public interface CreateProductTypeUseCaseSPI {
    ProductType execute(ProductType productType);
}
