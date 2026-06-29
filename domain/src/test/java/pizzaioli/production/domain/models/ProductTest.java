package pizzaioli.production.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import pizzaioli.production.domain.exceptions.ValueRequiredException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product — Domain model")
class ProductTest {

    private Product validProduct() {
        return new Product(1L, "Harina", 500.0, "GR", 1L
                , 1L, true, LocalDateTime.now());
    }

    @Nested
    @DisplayName("Constructor(all args)")
    class FullConstructor {

        @Test
        @DisplayName("Debe crear el producto cuando todos los campos son válidos")
        void shouldCreateProductWithValidFields() {
            LocalDateTime now = LocalDateTime.now();

            Product product = new Product(1L, "Harina", 500.0, "GR", 1L
                    , 2L, true, now);

            assertEquals(1L, product.getId());
            assertEquals("Harina", product.getName());
            assertEquals(500.0, product.getAmountValue());
            assertEquals("GR", product.getMeasurementUnitCode());
            assertEquals(1L, product.getProductCurrencyId());
            assertEquals(2L, product.getProductTypeId());
            assertTrue(product.isActive());
            assertEquals(now, product.getCreatedDate());
        }


        @ParameterizedTest(name = "name=''{0}''")
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("Debe lanzar ValueRequiredException cuando name es nulo, vacío o solo espacios")
        void shouldThrowWhenNameIsBlankOrNull(String name) {
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> new Product(null, name, 500.0, "GR", 1L
                            , 1L, true, null)
            );
            assertTrue(ex.getMessage().contains("name"));
        }


        @ParameterizedTest(name = "measurementUnitCode=''{0}''")
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("Debe lanzar ValueRequiredException cuando measurementUnitCode es nulo, vacío o solo espacios")
        void shouldThrowWhenMeasurementUnitCodeIsBlankOrNull(String code) {
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> new Product(null, "Harina", 500.0, code, 1L, 1L
                            , true, null)
            );
            assertTrue(ex.getMessage().contains("measurementUnitCode"));
        }

        @Test
        @DisplayName("Debe lanzar ValueRequiredException cuando amountValue es null")
        void shouldThrowWhenAmountValueIsNull() {
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> new Product(null, "Harina", null, "GR", 1L
                            , 1L, true, null)
            );
            assertTrue(ex.getMessage().contains("amountValue"));
        }


        @Test
        @DisplayName("Debe lanzar ValueRequiredException cuando productCurrencyId es null")
        void shouldThrowWhenProductCurrencyIdIsNull() {
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> new Product(null, "Harina", 500.0, "GR", null
                            , 1L, true, null)
            );
            assertTrue(ex.getMessage().contains("productCurrencyId"));
        }

        @Test
        @DisplayName("Debe lanzar ValueRequiredException cuando productTypeId es null")
        void shouldThrowWhenProductTypeIdIsNull() {
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> new Product(null, "Harina", 500.0, "GR", 1L
                            , null, true, null)
            );
            assertTrue(ex.getMessage().contains("productTypeId"));
        }
    }


    @Nested
    @DisplayName("Constructor vacío")
    class NoArgsConstructor {

        @Test
        @DisplayName("Debe crear una instancia sin lanzar excepciones")
        void shouldCreateInstanceWithoutException() {
            assertDoesNotThrow(() -> new Product());
        }

        @Test
        @DisplayName("Todos los campos deben ser nulos o false por defecto")
        void shouldHaveDefaultValues() {
            Product product = new Product();

            assertNull(product.getId());
            assertNull(product.getName());
            assertNull(product.getAmountValue());
            assertNull(product.getMeasurementUnitCode());
            assertNull(product.getProductCurrencyId());
            assertNull(product.getProductTypeId());
            assertFalse(product.isActive());
            assertNull(product.getCreatedDate());
        }
    }

    @Nested
    @DisplayName("setName()")
    class SetName {

        @Test
        @DisplayName("Debe asignar un nombre válido")
        void shouldSetValidName() {
            Product product = validProduct();
            product.setName("Azúcar");
            assertEquals("Azúcar", product.getName());
        }

        @ParameterizedTest(name = "name=''{0}''")
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("Debe lanzar ValueRequiredException con nombre nulo, vacío o solo espacios")
        void shouldThrowWhenNameIsInvalid(String name) {
            Product product = validProduct();
            assertThrows(ValueRequiredException.class, () -> product.setName(name));
        }
    }

    @Nested
    @DisplayName("setAmountValue()")
    class SetAmountValue {

        @Test
        @DisplayName("Debe asignar un valor de monto válido")
        void shouldSetValidAmountValue() {
            Product product = validProduct();
            product.setAmountValue(999.99);
            assertEquals(999.99, product.getAmountValue());
        }

        @Test
        @DisplayName("Debe lanzar ValueRequiredException cuando amountValue es null")
        void shouldThrowWhenAmountValueIsNull() {
            Product product = validProduct();
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> product.setAmountValue(null)
            );
            assertTrue(ex.getMessage().contains("amountValue"));
        }
    }

    @Nested
    @DisplayName("setMeasurementUnitCode()")
    class SetMeasurementUnitCode {

        @Test
        @DisplayName("Debe asignar un código de unidad de medida válido")
        void shouldSetValidCode() {
            Product product = validProduct();
            product.setMeasurementUnitCode("UN");
            assertEquals("UN", product.getMeasurementUnitCode());
        }

        @ParameterizedTest(name = "code=''{0}''")
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("Debe lanzar ValueRequiredException con código nulo, vacío o solo espacios")
        void shouldThrowWhenCodeIsInvalid(String code) {
            Product product = validProduct();
            assertThrows(ValueRequiredException.class, () -> product.setMeasurementUnitCode(code));
        }
    }

    @Nested
    @DisplayName("setProductCurrencyId()")
    class SetProductCurrencyId {

        @Test
        @DisplayName("Debe asignar un id de moneda válido")
        void shouldSetValidProductCurrencyId() {
            Product product = validProduct();
            product.setProductCurrencyId(5L);
            assertEquals(5L, product.getProductCurrencyId());
        }

        @Test
        @DisplayName("Debe lanzar ValueRequiredException cuando productCurrencyId es null")
        void shouldThrowWhenProductCurrencyIdIsNull() {
            Product product = validProduct();
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> product.setProductCurrencyId(null)
            );
            assertTrue(ex.getMessage().contains("productCurrencyId"));
        }
    }

    @Nested
    @DisplayName("setProductTypeId()")
    class SetProductTypeId {

        @Test
        @DisplayName("Debe asignar un id de tipo de producto válido")
        void shouldSetValidProductTypeId() {
            Product product = validProduct();
            product.setProductTypeId(3L);
            assertEquals(3L, product.getProductTypeId());
        }

        @Test
        @DisplayName("Debe lanzar ValueRequiredException cuando productTypeId es null")
        void shouldThrowWhenProductTypeIdIsNull() {
            Product product = validProduct();
            ValueRequiredException ex = assertThrows(
                    ValueRequiredException.class,
                    () -> product.setProductTypeId(null)
            );
            assertTrue(ex.getMessage().contains("productTypeId"));
        }
    }

    @Nested
    @DisplayName("setActive() / setId() / setCreatedDate()")
    class OtherSetters {

        @Test
        @DisplayName("setActive() debe cambiar el estado del producto")
        void shouldSetActive() {
            Product product = validProduct();
            product.setActive(false);
            assertFalse(product.isActive());
            product.setActive(true);
            assertTrue(product.isActive());
        }

        @Test
        @DisplayName("setId() debe asignar el id correctamente")
        void shouldSetId() {
            Product product = validProduct();
            product.setId(99L);
            assertEquals(99L, product.getId());
        }

        @Test
        @DisplayName("setCreatedDate() debe asignar la fecha correctamente")
        void shouldSetCreatedDate() {
            Product product = validProduct();
            LocalDateTime date = LocalDateTime.of(2024, 1, 15, 10, 30);
            product.setCreatedDate(date);
            assertEquals(date, product.getCreatedDate());
        }
    }
}
