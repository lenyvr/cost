package pizzaioli.production.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.models.Product;
import pizzaioli.production.domain.ports.output.ProductRepositorySPI;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductUseCase")
class ProductUseCaseTest {

    @Mock
    private ProductRepositorySPI productRepositorySPI;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product buildProduct(Long id, String name, boolean active, LocalDateTime createdDate) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setAmountValue(100.0);
        p.setMeasurementUnitCode("GR");
        p.setProductCurrencyId(1L);
        p.setProductTypeId(1L);
        p.setActive(active);
        p.setCreatedDate(createdDate);
        return p;
    }

    private Product buildNewProductRequest() {
        Product p = new Product();
        p.setName("Harina");
        p.setAmountValue(500.0);
        p.setMeasurementUnitCode("GR");
        p.setProductCurrencyId(1L);
        p.setProductTypeId(1L);
        return p;
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("Debe registrar un producto nuevo cuando no existe ninguno con el mismo nombre")
        void shouldCreateProductWhenNameDoesNotExist() {
            // Given
            Product request = buildNewProductRequest();
            Product saved = buildProduct(1L, "Harina", true, LocalDateTime.now());

            when(productRepositorySPI.getByName("Harina")).thenReturn(null);
            when(productRepositorySPI.save(any(Product.class))).thenReturn(saved);

            // When
            Product result = productUseCase.create(request);

            // Then
            assertNotNull(result);
            assertEquals("Harina", result.getName());
            assertTrue(result.isActive());
            verify(productRepositorySPI).getByName("Harina");
            verify(productRepositorySPI).save(any(Product.class));
        }

        @Test
        @DisplayName("Debe lanzar RecordAlreadyExistsException cuando ya existe un producto activo con el mismo nombre")
        void shouldThrowExceptionWhenActiveProductWithSameNameExists() {
            // Given
            Product request = buildNewProductRequest();
            Product existing = buildProduct(1L, "Harina", true, LocalDateTime.now());

            when(productRepositorySPI.getByName("Harina")).thenReturn(existing);

            // When / Then
            RecordAlreadyExistsException ex = assertThrows(
                    RecordAlreadyExistsException.class,
                    () -> productUseCase.create(request)
            );
            assertTrue(ex.getMessage().contains("Harina"));
            verify(productRepositorySPI).getByName("Harina");
            verify(productRepositorySPI, never()).save(any());
        }

        @Test
        @DisplayName("Debe reactivar y actualizar un producto inactivo con el mismo nombre (upsert)")
        void shouldReactivateInactiveProductWithSameName() {
            // Given
            LocalDateTime originalDate = LocalDateTime.of(2024, 1, 1, 0, 0);
            Product request = buildNewProductRequest();
            request.setAmountValue(999.0); // valor actualizado

            Product inactive = buildProduct(42L, "Harina", false, originalDate);

            Product saved = buildProduct(42L, "Harina", true, originalDate);
            saved.setAmountValue(999.0);

            when(productRepositorySPI.getByName("Harina")).thenReturn(inactive);
            when(productRepositorySPI.save(any(Product.class))).thenReturn(saved);

            // When
            Product result = productUseCase.create(request);

            // Then
            assertNotNull(result);
            assertTrue(result.isActive());
            assertEquals(42L, result.getId());
            assertEquals(originalDate, result.getCreatedDate());
            assertEquals(999.0, result.getAmountValue());
            verify(productRepositorySPI).save(any(Product.class));
        }

        @Test
        @DisplayName("El producto guardado debe tener active=true independientemente de lo que venga en el request")
        void shouldAlwaysSetActiveTrueOnCreate() {
            // Given
            Product request = buildNewProductRequest();
            request.setActive(false); // el caller envía false, el UC debe ignorarlo

            Product saved = buildProduct(1L, "Harina", true, LocalDateTime.now());

            when(productRepositorySPI.getByName("Harina")).thenReturn(null);
            when(productRepositorySPI.save(any(Product.class))).thenAnswer(inv -> {
                Product p = inv.getArgument(0);
                assertTrue(p.isActive(), "active debe ser true al guardar");
                return saved;
            });

            // When
            productUseCase.create(request);

            verify(productRepositorySPI).save(any(Product.class));
        }
    }
}
