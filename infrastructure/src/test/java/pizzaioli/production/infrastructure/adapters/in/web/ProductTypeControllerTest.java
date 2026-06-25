package pizzaioli.production.infrastructure.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pizzaioli.production.application.usecases.port.ProductTypeUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.exceptions.RecordHasDependenciesException;
import pizzaioli.production.domain.exceptions.RecordNotFoundException;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.infrastructure.dtos.request.CreateProductTypeRequestDTO;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductTypeController.class)
class ProductTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductTypeUseCaseSPI productTypeUseCaseSPI;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_WhenValidRequest_ShouldReturnCreated() throws Exception {
        // Arrange
        CreateProductTypeRequestDTO request = new CreateProductTypeRequestDTO("Ingrediente");
        ProductType createdProductType = new ProductType(1, "Ingrediente", true, LocalDateTime.now());

        when(productTypeUseCaseSPI.create(any(ProductType.class))).thenReturn(createdProductType);

        // Act & Assert
        mockMvc.perform(post("/api/v1/product-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ingrediente"));
    }

    @Test
    void create_WhenTypeAlreadyExists_ShouldReturnConflict() throws Exception {
        // Arrange
        CreateProductTypeRequestDTO request = new CreateProductTypeRequestDTO("Ingrediente");

        when(productTypeUseCaseSPI.create(any(ProductType.class)))
                .thenThrow(new RecordAlreadyExistsException("Product type with name 'Ingrediente' already exists."));

        // Act & Assert
        mockMvc.perform(post("/api/v1/product-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Product type with name 'Ingrediente' already exists."));
    }

    @Test
    void delete_WhenValidName_ShouldReturnNoContent() throws Exception {
        // Arrange
        String name = "Ingrediente";
        doNothing().when(productTypeUseCaseSPI).delete(name);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/product-types/{name}", name))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_WhenTypeNotFoundOrInactive_ShouldReturnNotFound() throws Exception {
        // Arrange
        String name = "NOT_EXIST";
        doThrow(new RecordNotFoundException("Product type with name 'NOT_EXIST' does not exist or is already inactive."))
                .when(productTypeUseCaseSPI).delete(name);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/product-types/{name}", name))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product type with name 'NOT_EXIST' does not exist or is already inactive."));
    }

    @Test
    void delete_WhenTypeHasDependencies_ShouldReturnConflict() throws Exception {
        // Arrange
        String name = "Ingrediente";
        doThrow(new RecordHasDependenciesException("No es posible eliminar el registro porque tiene dependencias."))
                .when(productTypeUseCaseSPI).delete(name);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/product-types/{name}", name))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("No es posible eliminar el registro porque tiene dependencias."));
    }
}
