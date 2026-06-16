package pizzaioli.production.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pizzaioli.production.application.usecases.CreateMeasurementUnitUseCase;
import pizzaioli.production.application.usecases.port.CreateMeasurementUnitUseCaseSPI;
import pizzaioli.production.application.usecases.DeleteMeasurementUnitUseCase;
import pizzaioli.production.application.usecases.port.DeleteMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateMeasurementUnitUseCaseSPI createMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI) {
        return new CreateMeasurementUnitUseCase(measurementUnitRepositorySPI);
    }

    @Bean
    public DeleteMeasurementUnitUseCaseSPI deleteMeasurementUnitUseCase(MeasurementUnitRepositorySPI measurementUnitRepositorySPI) {
        return new DeleteMeasurementUnitUseCase(measurementUnitRepositorySPI);
    }
}
