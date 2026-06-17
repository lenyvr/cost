package pizzaioli.production.domain.models;

import pizzaioli.production.domain.validation.ValidateEmptyField;

import java.time.LocalDateTime;

public class MeasurementUnit implements ValidateEmptyField {
    private String code;
    private String name;
    private boolean active;
    private LocalDateTime createdDate;

    public MeasurementUnit(String code, String name, boolean active, LocalDateTime createdDate) {
        validateEmptyField(name,"name");
        validateEmptyField(code,"code");
        this.code = code;
        this.name = name;
        this.active = active;
        this.createdDate = createdDate;
    }

    public MeasurementUnit() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        validateEmptyField(code,"code");
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateEmptyField(name,"name");
        this.name = name;
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
