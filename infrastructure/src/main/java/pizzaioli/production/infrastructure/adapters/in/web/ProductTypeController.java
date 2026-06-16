package pizzaioli.production.infrastructure.adapters.in.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaioli.production.application.usecases.port.CreateProductTypeUseCaseSPI;
import pizzaioli.production.application.usecases.port.DeleteProductTypeUseCaseSPI;
import pizzaioli.production.domain.models.ProductType;
import pizzaioli.production.infrastructure.dtos.request.CreateProductTypeRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.ProductTypeResponseDTO;

@RestController
@RequestMapping("/api/v1/product-types")
public class ProductTypeController {

    private final CreateProductTypeUseCaseSPI createProductTypeUseCaseSPI;
    private final DeleteProductTypeUseCaseSPI deleteProductTypeUseCaseSPI;

    public ProductTypeController(CreateProductTypeUseCaseSPI createProductTypeUseCaseSPI,
                                 DeleteProductTypeUseCaseSPI deleteProductTypeUseCaseSPI) {
        this.createProductTypeUseCaseSPI = createProductTypeUseCaseSPI;
        this.deleteProductTypeUseCaseSPI = deleteProductTypeUseCaseSPI;
    }

    @PostMapping
    public ResponseEntity<ProductTypeResponseDTO> create(@Valid @RequestBody CreateProductTypeRequestDTO requestDTO) {
        ProductType newProductType = new ProductType();
        newProductType.setName(requestDTO.name());
        newProductType.setActive(true);

        ProductType createdProductType = createProductTypeUseCaseSPI.execute(newProductType);

        ProductTypeResponseDTO responseDTO = new ProductTypeResponseDTO(
                createdProductType.getId(),
                createdProductType.getName()
        );

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        deleteProductTypeUseCaseSPI.execute(name);
        return ResponseEntity.noContent().build();
    }
}
