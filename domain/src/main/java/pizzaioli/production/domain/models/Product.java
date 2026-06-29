package pizzaioli.production.domain.models;

import pizzaioli.production.domain.validation.ValidateEmptyField;

import java.time.LocalDateTime;
import java.util.Optional;

public class Product implements ValidateEmptyField {

    private Long id;
    private String name;
    private Double amountValue;
    private String measurementUnitCode;
    private Long productCurrencyId;
    private Long productTypeId;
    private boolean active;
    private LocalDateTime createdDate;

    public Product(Long id, String name, Double amountValue, String measurementUnitCode,
                   Long productCurrencyId, Long productTypeId, boolean active, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.amountValue = amountValue;
        this.measurementUnitCode = measurementUnitCode;
        this.productCurrencyId = productCurrencyId;
        this.productTypeId = productTypeId;
        this.active = active;
        this.createdDate = createdDate;
        validateRequiredFields();
    }

    public Product() {
    }

    private void validateRequiredFields() {
        validateEmptyField(name, "name");
        validateEmptyField(measurementUnitCode, "measurementUnitCode");
        validateEmptyField(Optional.ofNullable(amountValue).map(String::valueOf).orElse(null), "amountValue");
        validateEmptyField(Optional.ofNullable(productCurrencyId).map(String::valueOf).orElse(null), "productCurrencyId");
        validateEmptyField(Optional.ofNullable(productTypeId).map(String::valueOf).orElse(null), "productTypeId");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateEmptyField(name, "name");
        this.name = name;
    }

    public Double getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(Double amountValue) {
        validateEmptyField(Optional.ofNullable(amountValue).map(String::valueOf).orElse(null), "amountValue");
        this.amountValue = amountValue;
    }

    public String getMeasurementUnitCode() {
        return measurementUnitCode;
    }

    public void setMeasurementUnitCode(String measurementUnitCode) {
        validateEmptyField(measurementUnitCode, "measurementUnitCode");
        this.measurementUnitCode = measurementUnitCode;
    }

    public Long getProductCurrencyId() {
        return productCurrencyId;
    }

    public void setProductCurrencyId(Long productCurrencyId) {
        validateEmptyField(Optional.ofNullable(productCurrencyId).map(String::valueOf).orElse(null), "productCurrencyId");
        this.productCurrencyId = productCurrencyId;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        validateEmptyField(Optional.ofNullable(productTypeId).map(String::valueOf).orElse(null), "productTypeId");
        this.productTypeId = productTypeId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
