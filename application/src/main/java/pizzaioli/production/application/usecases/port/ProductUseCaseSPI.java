package pizzaioli.production.application.usecases.port;

import pizzaioli.production.domain.models.Product;

public interface ProductUseCaseSPI {
    Product create(Product product);
}
