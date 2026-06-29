package pizzaioli.production.infrastructure.adapters.in.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzaioli.production.application.usecases.port.ProductUseCaseSPI;
import pizzaioli.production.domain.models.Product;
import pizzaioli.production.infrastructure.dtos.request.CreateProductRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.ProductResponseDTO;
import pizzaioli.production.infrastructure.mapper.ProductMapper;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductUseCaseSPI productUseCaseSPI;

    public ProductController(ProductUseCaseSPI productUseCaseSPI) {
        this.productUseCaseSPI = productUseCaseSPI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@Valid @RequestBody CreateProductRequestDTO requestDTO) {
        Product createdProduct = productUseCaseSPI.create(ProductMapper.toDomainFromDTO(requestDTO));
        return ProductMapper.toDTOFromDomain(createdProduct);
    }
}
