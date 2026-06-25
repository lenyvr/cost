package pizzaioli.production.infrastructure.adapters.in.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaioli.production.application.usecases.port.ProductTypeUseCaseSPI;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.infrastructure.dtos.request.CreateProductTypeRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.ProductTypeResponseDTO;
import pizzaioli.production.infrastructure.mapper.ProductTypeMapper;

@RestController
@RequestMapping("/api/v1/product-types")
public class ProductTypeController {

    private final ProductTypeUseCaseSPI productTypeUseCaseSPI;

    public ProductTypeController(ProductTypeUseCaseSPI productTypeUseCaseSPI) {
        this.productTypeUseCaseSPI = productTypeUseCaseSPI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductTypeResponseDTO create(@Valid @RequestBody CreateProductTypeRequestDTO requestDTO) {
        ProductType createdProductType = productTypeUseCaseSPI.create(ProductTypeMapper.toDomainFromDTO(requestDTO));
        return ProductTypeMapper.toDTOFromDomain(createdProductType);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        productTypeUseCaseSPI.delete(name);
    }
}
