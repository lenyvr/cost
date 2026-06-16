package pizzaioli.production.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.domain.ports.output.ProductTypeRepositorySPI;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductTypeUseCaseTest {

    @Mock
    private ProductTypeRepositorySPI repositorySPI;

    @InjectMocks
    private CreateProductTypeUseCase useCase;

    @Test
    void execute_WhenProductTypeDoesNotExist_ShouldSaveAndReturn() {
        // Arrange
        ProductType newProductType = new ProductType(null, "Ingrediente", true, LocalDateTime.now());
        when(repositorySPI.getByName("Ingrediente")).thenReturn(null);
        when(repositorySPI.save(newProductType)).thenReturn(new ProductType(1, "Ingrediente", true, newProductType.getCreatedDate()));

        // Act
        ProductType result = useCase.execute(newProductType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repositorySPI).save(newProductType);
    }

    @Test
    void execute_WhenProductTypeExistsAndIsActive_ShouldThrowException() {
        // Arrange
        ProductType existingProductType = new ProductType(1, "Ingrediente", true, LocalDateTime.now());
        when(repositorySPI.getByName("Ingrediente")).thenReturn(existingProductType);

        ProductType newProductType = new ProductType(null, "Ingrediente", true, LocalDateTime.now());

        // Act & Assert
        assertThrows(RecordAlreadyExistsException.class, () -> useCase.execute(newProductType));
        verify(repositorySPI, never()).save(any());
    }

    @Test
    void execute_WhenProductTypeExistsAndIsInactive_ShouldActivateAndSave() {
        // Arrange
        ProductType existingInactive = new ProductType(1, "Ingrediente", false, LocalDateTime.now());
        when(repositorySPI.getByName("Ingrediente")).thenReturn(existingInactive);
        when(repositorySPI.save(existingInactive)).thenReturn(existingInactive);

        ProductType newProductType = new ProductType(null, "Ingrediente", true, LocalDateTime.now());

        // Act
        ProductType result = useCase.execute(newProductType);

        // Assert
        assertTrue(result.isActive());
        verify(repositorySPI).save(existingInactive);
    }
}
