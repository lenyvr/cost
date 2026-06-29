package pizzaioli.production.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pizzaioli.production.application.usecases.MeasurementUnitUseCase;
import pizzaioli.production.application.usecases.ProductCurrencyUseCase;
import pizzaioli.production.application.usecases.ProductUseCase;
import pizzaioli.production.application.usecases.port.MeasurementUnitUseCaseSPI;
import pizzaioli.production.application.usecases.port.ProductCurrencyUseCaseSPI;
import pizzaioli.production.application.usecases.port.ProductUseCaseSPI;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

import pizzaioli.production.domain.ports.output.ProductRepositorySPI;

import pizzaioli.production.application.usecases.ProductTypeUseCase;
import pizzaioli.production.application.usecases.port.ProductTypeUseCaseSPI;
import pizzaioli.production.domain.ports.output.ProductTypeRepositorySPI;

@Configuration
public class BeanConfiguration {

    @Bean
    public MeasurementUnitUseCaseSPI createMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI
            ,  ProductRepositorySPI productRepositorySPI) {
        return new MeasurementUnitUseCase(measurementUnitRepositorySPI, productRepositorySPI);
    }

    @Bean
    public ProductTypeUseCaseSPI createProductTypeUseCase(ProductTypeRepositorySPI productTypeRepositorySPI
    , ProductRepositorySPI productRepositorySPI) {
        return new ProductTypeUseCase(productTypeRepositorySPI, productRepositorySPI);
    }

    @Bean
    public ProductCurrencyUseCaseSPI createProductCurrencyUseCase(pizzaioli.production.domain.ports.output.ProductCurrencyRepositorySPI productCurrencyRepositorySPI
    ,ProductRepositorySPI productRepositorySPI) {
        return new ProductCurrencyUseCase(productCurrencyRepositorySPI, productRepositorySPI);
    }

    @Bean
    public ProductUseCaseSPI createProductUseCase(ProductRepositorySPI productRepositorySPI) {
        return new ProductUseCase(productRepositorySPI);
    }
}

