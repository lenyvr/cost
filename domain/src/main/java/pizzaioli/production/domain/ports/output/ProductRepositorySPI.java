package pizzaioli.production.domain.ports.output;

public interface ProductRepositorySPI {
    boolean existsActiveProductByMeasurementUnitCode(String measurementUnitCode);
    boolean existsActiveProductByProductTypeId(Integer productTypeId);
    boolean existsActiveProductByProductCurrencyId(Integer productCurrencyId);
}
