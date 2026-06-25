package pizzaioli.production.domain.models;

import org.junit.jupiter.api.Test;
import pizzaioli.production.domain.exceptions.ValueRequiredException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @Test
    void testAllArgsConstructor() {
        Integer expectedId = 1;
        String expectedName = "Pizza";
        boolean expectedActive = true;
        LocalDateTime expectedCreatedDate = LocalDateTime.now();

        ProductType productType = new ProductType(expectedId, expectedName, expectedActive, expectedCreatedDate);

        assertEquals(expectedId, productType.getId());
        assertEquals(expectedName, productType.getName());
        assertEquals(expectedActive, productType.isActive());
        assertEquals(expectedCreatedDate, productType.getCreatedDate());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        ProductType productType = new ProductType();

        assertNull(productType.getId());
        assertNull(productType.getName());
        assertFalse(productType.isActive());
        assertNull(productType.getCreatedDate());

        Integer expectedId = 2;
        String expectedName = "Drink";
        boolean expectedActive = false;
        LocalDateTime expectedCreatedDate = LocalDateTime.now();

        productType.setId(expectedId);
        productType.setName(expectedName);
        productType.setActive(expectedActive);
        productType.setCreatedDate(expectedCreatedDate);

        assertEquals(expectedId, productType.getId());
        assertEquals(expectedName, productType.getName());
        assertFalse(productType.isActive());
        assertEquals(expectedCreatedDate, productType.getCreatedDate());
    }

    @Test
    void testConstructorValidatesName() {
        assertThrows(ValueRequiredException.class, () -> {
            new ProductType(1, null, true, LocalDateTime.now());
        });

        assertThrows(ValueRequiredException.class, () -> {
            new ProductType(1, "", true, LocalDateTime.now());
        });

        assertThrows(ValueRequiredException.class, () -> {
            new ProductType(1, "   ", true, LocalDateTime.now());
        });
    }

    @Test
    void testSetNameValidatesName() {
        ProductType productType = new ProductType();

        assertThrows(ValueRequiredException.class, () -> {
            productType.setName(null);
        });

        assertThrows(ValueRequiredException.class, () -> {
            productType.setName("");
        });

        assertThrows(ValueRequiredException.class, () -> {
            productType.setName("  ");
        });
    }
}
