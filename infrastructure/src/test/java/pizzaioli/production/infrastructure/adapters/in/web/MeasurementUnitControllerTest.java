package pizzaioli.production.infrastructure.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pizzaioli.production.application.usecases.port.CreateMeasurementUnitUseCaseSPI;
import pizzaioli.production.application.usecases.port.DeleteMeasurementUnitUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.models.MeasurementUnit;
import pizzaioli.production.infrastructure.dtos.request.CreateMeasurementUnitRequestDTO;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeasurementUnitController.class)
class MeasurementUnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateMeasurementUnitUseCaseSPI createMeasurementUnitUseCaseSPI;

    @MockitoBean
    private DeleteMeasurementUnitUseCaseSPI deleteMeasurementUnitUseCaseSPI;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_WhenValidRequest_ShouldReturnCreatedAndMeasurementUnit() throws Exception {
        // Arrange
        CreateMeasurementUnitRequestDTO request = new CreateMeasurementUnitRequestDTO("GR", "Gramo");
        MeasurementUnit savedUnit = new MeasurementUnit("GR", "Gramo", true, LocalDateTime.now());

        when(createMeasurementUnitUseCaseSPI.execute(any(MeasurementUnit.class))).thenReturn(savedUnit);

        // Act & Assert
        mockMvc.perform(post("/api/v1/measurement-units")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("GR"))
                .andExpect(jsonPath("$.name").value("Gramo"));
    }

    @Test
    void create_WhenUnitAlreadyExists_ShouldReturnConflict() throws Exception {
        // Arrange
        CreateMeasurementUnitRequestDTO request = new CreateMeasurementUnitRequestDTO("GR", "Gramo");

        when(createMeasurementUnitUseCaseSPI.execute(any(MeasurementUnit.class)))
                .thenThrow(new RecordAlreadyExistsException("Measurement Unit with code 'GR' already exists."));

        // Act & Assert
        mockMvc.perform(post("/api/v1/measurement-units")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Measurement Unit with code 'GR' already exists."));
    }

    @Test
    void delete_WhenValidCode_ShouldReturnNoContent() throws Exception {
        // Arrange
        String code = "GR";
        doNothing().when(deleteMeasurementUnitUseCaseSPI).execute(code);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/measurement-units/{code}", code))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_WhenUnitNotFoundOrInactive_ShouldReturnNotFound() throws Exception {
        // Arrange
        String code = "NOT_EXIST";
        doThrow(new RecordNotFoundException("Measurement unit with code 'NOT_EXIST' does not exist or is already inactive."))
                .when(deleteMeasurementUnitUseCaseSPI).execute(code);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/measurement-units/{code}", code))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Measurement unit with code 'NOT_EXIST' does not exist or is already inactive."));
    }

    @Test
    void delete_WhenUnitHasDependencies_ShouldReturnConflict() throws Exception {
        // Arrange
        String code = "GR";
        doThrow(new RecordHasDependenciesException("No es posible eliminar el registro porque tiene dependencias."))
                .when(deleteMeasurementUnitUseCaseSPI).execute(code);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/measurement-units/{code}", code))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("No es posible eliminar el registro porque tiene dependencias."));
    }
}
