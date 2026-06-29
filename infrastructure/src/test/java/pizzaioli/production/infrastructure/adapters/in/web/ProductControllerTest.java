package pizzaioli.production.infrastructure.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pizzaioli.production.application.usecases.port.ProductUseCaseSPI;
import pizzaioli.production.domain.exceptions.RecordAlreadyExistsException;
import pizzaioli.production.domain.models.Product;
import pizzaioli.production.infrastructure.exception.GlobalExceptionHandler;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("ProductController — Integration Tests")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductUseCaseSPI productUseCaseSPI;

    private Product buildSavedProduct() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Harina");
        p.setAmountValue(500.0);
        p.setMeasurementUnitCode("GR");
        p.setProductCurrencyId(1L);
        p.setProductTypeId(1L);
        p.setActive(true);
        p.setCreatedDate(LocalDateTime.of(2024, 6, 1, 10, 0));
        return p;
    }

    record CreateProductRequest(
            String name,
            Double amountValue,
            String measurementUnitCode,
            Long productCurrencyId,
            Long productTypeId
    ) {}

    @Nested
    @DisplayName("POST /api/v1/products")
    class CreateProduct {

        @Test
        @DisplayName("201 Created — request válido devuelve el producto creado")
        void shouldReturn201WhenRequestIsValid() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "Harina", 500.0, "GR", 1L, 1L
            );
            when(productUseCaseSPI.create(any(Product.class))).thenReturn(buildSavedProduct());

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Harina"))
                    .andExpect(jsonPath("$.amountValue").value(500.0))
                    .andExpect(jsonPath("$.measurementUnitCode").value("GR"))
                    .andExpect(jsonPath("$.productCurrencyId").value(1))
                    .andExpect(jsonPath("$.productTypeId").value(1))
                    .andExpect(jsonPath("$.active").value(true));
        }

        @Test
        @DisplayName("409 Conflict — el use case lanza RecordAlreadyExistsException")
        void shouldReturn409WhenProductAlreadyExists() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "Harina", 500.0, "GR", 1L, 1L
            );
            when(productUseCaseSPI.create(any(Product.class)))
                    .thenThrow(new RecordAlreadyExistsException("Product with name 'Harina' already exists."));

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value("Product with name 'Harina' already exists."));
        }

        @Test
        @DisplayName("400 Bad Request — name en blanco")
        void shouldReturn400WhenNameIsBlank() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "", 500.0, "GR", 1L, 1L
            );

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("400 Bad Request — amountValue es null")
        void shouldReturn400WhenAmountValueIsNull() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "Harina", null, "GR", 1L, 1L
            );

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("400 Bad Request — amountValue es negativo")
        void shouldReturn400WhenAmountValueIsNegative() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "Harina", -1.0, "GR", 1L, 1L
            );

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("400 Bad Request — measurementUnitCode en blanco")
        void shouldReturn400WhenMeasurementUnitCodeIsBlank() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "Harina", 500.0, "", 1L, 1L
            );

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("400 Bad Request — productCurrencyId es null")
        void shouldReturn400WhenProductCurrencyIdIsNull() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "Harina", 500.0, "GR", null, 1L
            );

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("400 Bad Request — productTypeId es null")
        void shouldReturn400WhenProductTypeIdIsNull() throws Exception {
            // Given
            CreateProductRequest request = new CreateProductRequest(
                    "Harina", 500.0, "GR", 1L, null
            );

            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("400 Bad Request — body vacío")
        void shouldReturn400WhenBodyIsEmpty() throws Exception {
            // When / Then
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{}"))
                    .andExpect(status().isBadRequest());
        }
    }
}
