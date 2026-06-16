package pizzaioli.production.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzaioli.production.domain.exceptions.MeasurementUnitNotFoundException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

import pizzaioli.production.domain.ports.output.ProductRepositorySPI;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMeasurementUnitUseCaseTest {

    @Mock
    private MeasurementUnitRepositorySPI repositorySPI;

    @Mock
    private ProductRepositorySPI productRepositorySPI;

    @InjectMocks
    private DeleteMeasurementUnitUseCase useCase;

    @Test
    void execute_WhenUnitExistsAndIsActive_ShouldSetInactiveAndSave() {
        // Arrange
        String code = "GR";
        MeasurementUnit activeUnit = new MeasurementUnit(code, "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(code)).thenReturn(activeUnit);
        when(productRepositorySPI.existsActiveProductByMeasurementUnitCode(code)).thenReturn(false);

        // Act
        useCase.execute(code);

        // Assert
        assertFalse(activeUnit.isActive());
        verify(repositorySPI).save(activeUnit);
    }

    @Test
    void execute_WhenUnitDoesNotExist_ShouldThrowException() {
        // Arrange
        String code = "NOT_EXIST";
        when(repositorySPI.getByCode(code)).thenReturn(null);

        // Act & Assert
        assertThrows(MeasurementUnitNotFoundException.class, () -> useCase.execute(code));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByMeasurementUnitCode(anyString());
    }

    @Test
    void execute_WhenUnitExistsButIsInactive_ShouldThrowException() {
        // Arrange
        String code = "GR";
        MeasurementUnit inactiveUnit = new MeasurementUnit(code, "Gramo", false, LocalDateTime.now());
        when(repositorySPI.getByCode(code)).thenReturn(inactiveUnit);

        // Act & Assert
        assertThrows(MeasurementUnitNotFoundException.class, () -> useCase.execute(code));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByMeasurementUnitCode(anyString());
    }

    @Test
    void execute_WhenUnitHasActiveProducts_ShouldThrowException() {
        // Arrange
        String code = "GR";
        MeasurementUnit activeUnit = new MeasurementUnit(code, "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(code)).thenReturn(activeUnit);
        when(productRepositorySPI.existsActiveProductByMeasurementUnitCode(code)).thenReturn(true);

        // Act & Assert
        assertThrows(pizzaioli.production.domain.exceptions.MeasurementUnitHasDependenciesException.class, () -> useCase.execute(code));
        verify(repositorySPI, never()).save(any());
    }
}
