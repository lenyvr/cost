package pizzaioli.production.domain.ports.output;

import pizzaioli.production.domain.models.ProductType;

public interface ProductTypeRepositorySPI {
    ProductType save(ProductType productType);
    ProductType getByName(String name);
}
