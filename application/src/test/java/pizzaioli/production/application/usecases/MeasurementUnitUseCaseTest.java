package pizzaioli.production.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeasurementUnitUseCaseTest {

    @Mock
    private MeasurementUnitRepositorySPI repositorySPI;

    @Mock
    private ProductRepositorySPI productRepositorySPI;

    @InjectMocks
    private MeasurementUnitUseCase useCase;

    private MeasurementUnit requestUnit;

    @BeforeEach
    void setUp() {
        requestUnit = new MeasurementUnit("GR", "Gramo", true, LocalDateTime.now());
    }

    @Test
    void createMeasurementUnit_WhenUnitDoesNotExist_ShouldCreateNewUnit() {
        // Arrange
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(null);
        when(repositorySPI.getByName(requestUnit.getName())).thenReturn(null);
        when(repositorySPI.save(any(MeasurementUnit.class))).thenReturn(requestUnit);

        // Act
        MeasurementUnit saved = useCase.create(requestUnit);

        // Assert
        assertNotNull(saved);
        assertEquals("GR", saved.getCode());
        verify(repositorySPI).save(requestUnit);
    }

    @Test
    void createWithCodeExists_ShouldThrowException() {
        // Arrange
        MeasurementUnit existingActive = new MeasurementUnit("GR", "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(existingActive);

        // Act & Assert
        assertThrows(RecordAlreadyExistsException.class, () -> useCase.create(requestUnit));
        verify(repositorySPI, never()).save(any());
    }

    @Test
    void createWithNameExists_ShouldThrowException() {
        // Arrange
        MeasurementUnit existingActive = new MeasurementUnit("OTHER", "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(null);
        when(repositorySPI.getByName(requestUnit.getName())).thenReturn(existingActive);

        // Act & Assert
        assertThrows(RecordAlreadyExistsException.class, () -> useCase.create(requestUnit));
        verify(repositorySPI, never()).save(any());
    }

    @Test
    void createWithCodeExists_ShouldReactivateAndSave() {
        // Arrange
        MeasurementUnit existingInactive = new MeasurementUnit("GR", "Old Name", false, LocalDateTime.now());
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(existingInactive);
        
        MeasurementUnit expectedSaved = new MeasurementUnit("GR", "Gramo", true, LocalDateTime.now());
        when(repositorySPI.save(any(MeasurementUnit.class))).thenReturn(expectedSaved);

        // Act
        MeasurementUnit saved = useCase.create(requestUnit);

        // Assert
        assertNotNull(saved);
        assertTrue(existingInactive.isActive()); // It should mutate existing
        assertEquals("Gramo", existingInactive.getName()); // It should mutate existing
        verify(repositorySPI).save(existingInactive);
    }

    @Test
    void deleteExistsAndIsActive_ShouldSetInactiveAndSave() {
        // Arrange
        String code = "GR";
        MeasurementUnit activeUnit = new MeasurementUnit(code, "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(code)).thenReturn(activeUnit);
        when(productRepositorySPI.existsActiveProductByMeasurementUnitCode(code)).thenReturn(false);

        // Act
        useCase.delete(code);

        // Assert
        assertFalse(activeUnit.isActive());
        verify(repositorySPI).save(activeUnit);
    }

    @Test
    void deleteDoesNotExist_ShouldThrowException() {
        // Arrange
        String code = "NOT_EXIST";
        when(repositorySPI.getByCode(code)).thenReturn(null);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> useCase.delete(code));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByMeasurementUnitCode(anyString());
    }

    @Test
    void deleteExistsButIsInactive_ShouldThrowException() {
        // Arrange
        String code = "GR";
        MeasurementUnit inactiveUnit = new MeasurementUnit(code, "Gramo", false, LocalDateTime.now());
        when(repositorySPI.getByCode(code)).thenReturn(inactiveUnit);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> useCase.delete(code));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByMeasurementUnitCode(anyString());
    }

    @Test
    void deleteHasActiveProducts_ShouldThrowException() {
        // Arrange
        String code = "GR";
        MeasurementUnit activeUnit = new MeasurementUnit(code, "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(code)).thenReturn(activeUnit);
        when(productRepositorySPI.existsActiveProductByMeasurementUnitCode(code)).thenReturn(true);

        // Act & Assert
        assertThrows(pizzaioli.production.domain.exceptions.RecordHasDependenciesException.class, () -> useCase.delete(code));
        verify(repositorySPI, never()).save(any());
    }
}
