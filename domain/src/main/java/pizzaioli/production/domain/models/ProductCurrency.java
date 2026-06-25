package pizzaioli.production.domain.models;

import pizzaioli.production.domain.validation.ValidateEmptyField;

import java.time.LocalDateTime;

public class ProductCurrency  implements ValidateEmptyField {
    private Integer id;
    private String name;
    private String symbol;
    private String code;
    private String description;
    private boolean active;
    private LocalDateTime createdDate;

    public ProductCurrency(Integer id, String name, String symbol, String code, String description, boolean active, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.code = code;
        this.description = description;
        this.active = active;
        this.createdDate = createdDate;
        validateRequiredFields();
    }

    public ProductCurrency() {
    }

    private void validateRequiredFields(){
        validateEmptyField(name, "name");
        validateEmptyField(symbol, "symbol");
        validateEmptyField(code, "code");
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateEmptyField(name, "name");
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        validateEmptyField(symbol, "symbol");
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        validateEmptyField(code, "code");
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
