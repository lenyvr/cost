package pizzaioli.production.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pizzaioli.production.application.usecases.CreateMeasurementUnitUseCase;
import pizzaioli.production.application.usecases.port.CreateMeasurementUnitUseCaseSPI;
import pizzaioli.production.application.usecases.DeleteMeasurementUnitUseCase;
import pizzaioli.production.application.usecases.port.DeleteMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

import pizzaioli.production.domain.ports.output.ProductRepositorySPI;

import pizzaioli.production.application.usecases.CreateProductTypeUseCase;
import pizzaioli.production.application.usecases.DeleteProductTypeUseCase;
import pizzaioli.production.application.usecases.port.CreateProductTypeUseCaseSPI;
import pizzaioli.production.application.usecases.port.DeleteProductTypeUseCaseSPI;
import pizzaioli.production.domain.ports.output.ProductTypeRepositorySPI;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateMeasurementUnitUseCaseSPI createMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI) {
        return new CreateMeasurementUnitUseCase(measurementUnitRepositorySPI);
    }

    @Bean
    public DeleteMeasurementUnitUseCaseSPI deleteMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI, 
                                                                        ProductRepositorySPI productRepositorySPI) {
        return new DeleteMeasurementUnitUseCase(measurementUnitRepositorySPI, productRepositorySPI);
    }

    @Bean
    public CreateProductTypeUseCaseSPI createProductTypeUseCase(ProductTypeRepositorySPI productTypeRepositorySPI) {
        return new CreateProductTypeUseCase(productTypeRepositorySPI);
    }

    @Bean
    public DeleteProductTypeUseCaseSPI deleteProductTypeUseCase(ProductTypeRepositorySPI productTypeRepositorySPI,
                                                                ProductRepositorySPI productRepositorySPI) {
        return new DeleteProductTypeUseCase(productTypeRepositorySPI, productRepositorySPI);
    }
}
