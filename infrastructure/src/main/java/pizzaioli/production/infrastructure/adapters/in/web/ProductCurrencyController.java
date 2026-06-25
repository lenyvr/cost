package pizzaioli.production.infrastructure.adapters.in.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzaioli.production.application.usecases.port.ProductCurrencyUseCaseSPI;
import pizzaioli.production.domain.models.ProductCurrency;
import pizzaioli.production.infrastructure.dtos.request.CreateProductCurrencyRequestDTO;
import pizzaioli.production.infrastructure.dtos.response.ProductCurrencyResponseDTO;
import pizzaioli.production.infrastructure.mapper.MeasurementUnitMapper;
import pizzaioli.production.infrastructure.mapper.ProductCurrencyMapper;

@RestController
@RequestMapping("/api/v1/product-currencies")
public class ProductCurrencyController {

    private final ProductCurrencyUseCaseSPI productCurrencyUseCaseSPI;

    public ProductCurrencyController(ProductCurrencyUseCaseSPI productCurrencyUseCaseSPI) {
        this.productCurrencyUseCaseSPI = productCurrencyUseCaseSPI;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCurrencyResponseDTO create(@Valid @RequestBody CreateProductCurrencyRequestDTO requestDTO) {
        ProductCurrency createdProductCurrency = productCurrencyUseCaseSPI
                .create(ProductCurrencyMapper.toDomainFromDTO(requestDTO));
        return ProductCurrencyMapper.toDTOFromDomain(createdProductCurrency);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        productCurrencyUseCaseSPI.delete(code);
    }
}
