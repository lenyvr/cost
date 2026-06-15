package pizzaioli.production.domain.models;

import java.time.LocalDateTime;

public class MeasurementUnit {
    private String code;
    private String name;
    private boolean active;
    private LocalDateTime createdDate;

    public MeasurementUnit(String code, String name, boolean active, LocalDateTime createdDate) {
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
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
