package pizzaioli.production.domain.ports.output;

import pizzaioli.production.domain.models.Product;

public interface ProductRepositorySPI {
    boolean existsActiveProductByMeasurementUnitCode(String measurementUnitCode);
    boolean existsActiveProductByProductTypeId(Integer productTypeId);
    boolean existsActiveProductByProductCurrencyId(Integer productCurrencyId);
    Product save(Product product);
    Product getByName(String name);
}
