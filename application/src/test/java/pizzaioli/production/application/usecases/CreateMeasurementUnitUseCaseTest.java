package pizzaioli.production.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.domain.ports.output.MeasurementUnitRepositorySPI;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMeasurementUnitUseCaseTest {

    @Mock
    private MeasurementUnitRepositorySPI repositorySPI;

    @InjectMocks
    private CreateMeasurementUnitUseCase useCase;

    private MeasurementUnit requestUnit;

    @BeforeEach
    void setUp() {
        requestUnit = new MeasurementUnit("GR", "Gramo", true, LocalDateTime.now());
    }

    @Test
    void execute_WhenUnitDoesNotExist_ShouldCreateNewUnit() {
        // Arrange
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(null);
        when(repositorySPI.getByName(requestUnit.getName())).thenReturn(null);
        when(repositorySPI.save(any(MeasurementUnit.class))).thenReturn(requestUnit);

        // Act
        MeasurementUnit saved = useCase.execute(requestUnit);

        // Assert
        assertNotNull(saved);
        assertEquals("GR", saved.getCode());
        verify(repositorySPI).save(requestUnit);
    }

    @Test
    void execute_WhenActiveUnitWithCodeExists_ShouldThrowException() {
        // Arrange
        MeasurementUnit existingActive = new MeasurementUnit("GR", "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(existingActive);

        // Act & Assert
        assertThrows(RecordAlreadyExistsException.class, () -> useCase.execute(requestUnit));
        verify(repositorySPI, never()).save(any());
    }

    @Test
    void execute_WhenActiveUnitWithNameExists_ShouldThrowException() {
        // Arrange
        MeasurementUnit existingActive = new MeasurementUnit("OTHER", "Gramo", true, LocalDateTime.now());
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(null);
        when(repositorySPI.getByName(requestUnit.getName())).thenReturn(existingActive);

        // Act & Assert
        assertThrows(RecordAlreadyExistsException.class, () -> useCase.execute(requestUnit));
        verify(repositorySPI, never()).save(any());
    }

    @Test
    void execute_WhenInactiveUnitWithCodeExists_ShouldReactivateAndSave() {
        // Arrange
        MeasurementUnit existingInactive = new MeasurementUnit("GR", "Old Name", false, LocalDateTime.now());
        when(repositorySPI.getByCode(requestUnit.getCode())).thenReturn(existingInactive);
        
        MeasurementUnit expectedSaved = new MeasurementUnit("GR", "Gramo", true, LocalDateTime.now());
        when(repositorySPI.save(any(MeasurementUnit.class))).thenReturn(expectedSaved);

        // Act
        MeasurementUnit saved = useCase.execute(requestUnit);

        // Assert
        assertNotNull(saved);
        assertTrue(existingInactive.isActive()); // It should mutate existing
        assertEquals("Gramo", existingInactive.getName()); // It should mutate existing
        verify(repositorySPI).save(existingInactive);
    }
}
