package pizzaioli.production.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;
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
    private ProductTypeUseCase useCase;

    @Mock
    private ProductRepositorySPI productRepositorySPI;

    @Test
    void create_WhenProductTypeDoesNotExist_ShouldSaveAndReturn() {
        // Arrange
        ProductType newProductType = new ProductType(null, "Ingrediente", true, LocalDateTime.now());
        when(repositorySPI.getByName("Ingrediente")).thenReturn(null);
        when(repositorySPI.save(newProductType)).thenReturn(new ProductType(1, "Ingrediente", true, newProductType.getCreatedDate()));

        // Act
        ProductType result = useCase.create(newProductType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repositorySPI).save(newProductType);
    }

    @Test
    void create_WhenProductTypeExistsAndIsActive_ShouldThrowException() {
        // Arrange
        ProductType existingProductType = new ProductType(1, "Ingrediente", true, LocalDateTime.now());
        when(repositorySPI.getByName("Ingrediente")).thenReturn(existingProductType);

        ProductType newProductType = new ProductType(null, "Ingrediente", true, LocalDateTime.now());

        // Act & Assert
        assertThrows(RecordAlreadyExistsException.class, () -> useCase.create(newProductType));
        verify(repositorySPI, never()).save(any());
    }

    @Test
    void create_WhenProductTypeExistsAndIsInactive_ShouldActivateAndSave() {
        // Arrange
        ProductType existingInactive = new ProductType(1, "Ingrediente", false, LocalDateTime.now());
        when(repositorySPI.getByName("Ingrediente")).thenReturn(existingInactive);
        ProductType newProductType = new ProductType(null, "Ingrediente", true, LocalDateTime.now());
        when(repositorySPI.save(newProductType)).thenReturn(newProductType);


        // Act
        ProductType result = useCase.create(newProductType);

        // Assert
        assertTrue(result.isActive());
        verify(repositorySPI).save(newProductType);
    }

    @Test
    void delete_WhenProductTypeExistsAndIsActive_ShouldSetInactiveAndSave() {
        // Arrange
        String name = "Ingrediente";
        ProductType activeProductType = new ProductType(1, name, true, LocalDateTime.now());
        when(repositorySPI.getByName(name)).thenReturn(activeProductType);
        when(productRepositorySPI.existsActiveProductByProductTypeId(1)).thenReturn(false);

        // Act
        useCase.delete(name);

        // Assert
        assertFalse(activeProductType.isActive());
        verify(repositorySPI).save(activeProductType);
    }

    @Test
    void delete_WhenProductTypeDoesNotExist_ShouldThrowException() {
        // Arrange
        String name = "NOT_EXIST";
        when(repositorySPI.getByName(name)).thenReturn(null);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> useCase.delete(name));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByProductTypeId(anyInt());
    }

    @Test
    void delete_WhenProductTypeExistsButIsInactive_ShouldThrowException() {
        // Arrange
        String name = "Ingrediente";
        ProductType inactiveProductType = new ProductType(1, name, false, LocalDateTime.now());
        when(repositorySPI.getByName(name)).thenReturn(inactiveProductType);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> useCase.delete(name));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByProductTypeId(anyInt());
    }

    @Test
    void delete_WhenProductTypeHasDependencies_ShouldThrowException() {
        // Arrange
        String name = "Ingrediente";
        ProductType activeProductType = new ProductType(1, name, true, LocalDateTime.now());
        when(repositorySPI.getByName(name)).thenReturn(activeProductType);
        when(productRepositorySPI.existsActiveProductByProductTypeId(1)).thenReturn(true);

        // Act & Assert
        assertThrows(RecordHasDependenciesException.class, () -> useCase.delete(name));
        verify(repositorySPI, never()).save(any());
    }
}
