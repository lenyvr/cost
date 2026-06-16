package pizzaioli.production.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class DeleteProductTypeUseCaseTest {

    @Mock
    private ProductTypeRepositorySPI repositorySPI;

    @Mock
    private ProductRepositorySPI productRepositorySPI;

    @InjectMocks
    private DeleteProductTypeUseCase useCase;

    @Test
    void execute_WhenProductTypeExistsAndIsActive_ShouldSetInactiveAndSave() {
        // Arrange
        String name = "Ingrediente";
        ProductType activeProductType = new ProductType(1, name, true, LocalDateTime.now());
        when(repositorySPI.getByName(name)).thenReturn(activeProductType);
        when(productRepositorySPI.existsActiveProductByProductTypeId(1)).thenReturn(false);

        // Act
        useCase.execute(name);

        // Assert
        assertFalse(activeProductType.isActive());
        verify(repositorySPI).save(activeProductType);
    }

    @Test
    void execute_WhenProductTypeDoesNotExist_ShouldThrowException() {
        // Arrange
        String name = "NOT_EXIST";
        when(repositorySPI.getByName(name)).thenReturn(null);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> useCase.execute(name));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByProductTypeId(anyInt());
    }

    @Test
    void execute_WhenProductTypeExistsButIsInactive_ShouldThrowException() {
        // Arrange
        String name = "Ingrediente";
        ProductType inactiveProductType = new ProductType(1, name, false, LocalDateTime.now());
        when(repositorySPI.getByName(name)).thenReturn(inactiveProductType);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> useCase.execute(name));
        verify(repositorySPI, never()).save(any());
        verify(productRepositorySPI, never()).existsActiveProductByProductTypeId(anyInt());
    }

    @Test
    void execute_WhenProductTypeHasDependencies_ShouldThrowException() {
        // Arrange
        String name = "Ingrediente";
        ProductType activeProductType = new ProductType(1, name, true, LocalDateTime.now());
        when(repositorySPI.getByName(name)).thenReturn(activeProductType);
        when(productRepositorySPI.existsActiveProductByProductTypeId(1)).thenReturn(true);

        // Act & Assert
        assertThrows(RecordHasDependenciesException.class, () -> useCase.execute(name));
        verify(repositorySPI, never()).save(any());
    }
}
