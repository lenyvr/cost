import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pizzaioli.production.domain.exceptions.ValueRequiredException;
import pizzaioli.production.domain.models.MeasurementUnit;

import java.time.LocalDateTime;

public class MeasurementUnitTest {

    @Test
    public void buildMeasurementUnitWithSuccess() {
        // Arrange
        ValueRequiredException valueRequiredException = null;
        MeasurementUnit measurementUnit=null;

        //Act
        try{
            measurementUnit = new MeasurementUnit("code","name", Boolean.TRUE, LocalDateTime.now());
        } catch (ValueRequiredException e) {
            valueRequiredException = e;
        }

        // Assert
        Assertions.assertNull(valueRequiredException,"Not exception must be trown");
        Assertions.assertNotNull(measurementUnit, "Measurement unit must not be null");
    }

    @Test
    public void buildMeasurementUnitWithErrorForEmptyName() {
        // Arrange
        ValueRequiredException valueRequiredException = null;
        MeasurementUnit measurementUnit=null;

        //Act
        try{
            measurementUnit = new MeasurementUnit("code","", Boolean.TRUE, LocalDateTime.now());
        } catch (ValueRequiredException e) {
            valueRequiredException = e;
        }

        // Assert
        Assertions.assertNotNull(valueRequiredException,"Exception must be trown");
        Assertions.assertEquals("The field name is required", valueRequiredException.getMessage());
        Assertions.assertNull(measurementUnit, "Measurement unit could not be initialized");
    }

    @Test
    public void buildMeasurementUnitWithErrorForEmptyCode() {
        // Arrange
        ValueRequiredException valueRequiredException = null;
        MeasurementUnit measurementUnit=null;

        //Act
        try{
            measurementUnit = new MeasurementUnit("","name", Boolean.TRUE, LocalDateTime.now());
        } catch (ValueRequiredException e) {
            valueRequiredException = e;
        }

        // Assert
        Assertions.assertNotNull(valueRequiredException,"Exception must be trown");
        Assertions.assertEquals("The field code is required", valueRequiredException.getMessage());
        Assertions.assertNull(measurementUnit, "Measurement unit could not be initialized");
    }
}
