package pizzaioli.production.domain.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductCurrencyTest {

    @Test
    void testAllArgsConstructor() {
        Integer expectedId = 1;
        String expectedName = "US Dollar";
        String expectedSymbol = "$";
        String expectedCode = "USD";
        String expectedDescription = "United States Dollar";
        boolean expectedActive = true;
        LocalDateTime expectedCreatedDate = LocalDateTime.now();

        ProductCurrency productCurrency = new ProductCurrency(
                expectedId, expectedName, expectedSymbol, expectedCode, expectedDescription, expectedActive, expectedCreatedDate
        );

        assertEquals(expectedId, productCurrency.getId());
        assertEquals(expectedName, productCurrency.getName());
        assertEquals(expectedSymbol, productCurrency.getSymbol());
        assertEquals(expectedCode, productCurrency.getCode());
        assertEquals(expectedDescription, productCurrency.getDescription());
        assertEquals(expectedActive, productCurrency.isActive());
        assertEquals(expectedCreatedDate, productCurrency.getCreatedDate());
    }

    @Test
    void testConstructorValidatesRequiredFields() {
        assertThrows(pizzaioli.production.domain.exceptions.ValueRequiredException.class, () -> {
            new ProductCurrency(1, null, "$", "USD", "Desc", true, LocalDateTime.now());
        });

        assertThrows(pizzaioli.production.domain.exceptions.ValueRequiredException.class, () -> {
            new ProductCurrency(1, "Name", null, "USD", "Desc", true, LocalDateTime.now());
        });

        assertThrows(pizzaioli.production.domain.exceptions.ValueRequiredException.class, () -> {
            new ProductCurrency(1, "Name", "$", "", "Desc", true, LocalDateTime.now());
        });
    }

    @Test
    void testSettersValidateRequiredFields() {
        ProductCurrency productCurrency = new ProductCurrency();
        
        assertThrows(pizzaioli.production.domain.exceptions.ValueRequiredException.class, () -> {
            productCurrency.setName(null);
        });

        assertThrows(pizzaioli.production.domain.exceptions.ValueRequiredException.class, () -> {
            productCurrency.setSymbol(" ");
        });

        assertThrows(pizzaioli.production.domain.exceptions.ValueRequiredException.class, () -> {
            productCurrency.setCode("");
        });
    }

    @Test
    void testNoArgsConstructorAndValidSetters() {
        ProductCurrency productCurrency = new ProductCurrency();

        assertNull(productCurrency.getId());
        assertNull(productCurrency.getName());
        assertNull(productCurrency.getSymbol());
        assertNull(productCurrency.getCode());
        assertNull(productCurrency.getDescription());
        assertFalse(productCurrency.isActive());
        assertNull(productCurrency.getCreatedDate());

        Integer expectedId = 2;
        String expectedName = "Euro";
        String expectedSymbol = "€";
        String expectedCode = "EUR";
        String expectedDescription = "Euro currency";
        boolean expectedActive = true;
        LocalDateTime expectedCreatedDate = LocalDateTime.now();

        productCurrency.setId(expectedId);
        productCurrency.setName(expectedName);
        productCurrency.setSymbol(expectedSymbol);
        productCurrency.setCode(expectedCode);
        productCurrency.setDescription(expectedDescription);
        productCurrency.setActive(expectedActive);
        productCurrency.setCreatedDate(expectedCreatedDate);

        assertEquals(expectedId, productCurrency.getId());
        assertEquals(expectedName, productCurrency.getName());
        assertEquals(expectedSymbol, productCurrency.getSymbol());
        assertEquals(expectedCode, productCurrency.getCode());
        assertEquals(expectedDescription, productCurrency.getDescription());
        assertTrue(productCurrency.isActive());
        assertEquals(expectedCreatedDate, productCurrency.getCreatedDate());
    }
}
