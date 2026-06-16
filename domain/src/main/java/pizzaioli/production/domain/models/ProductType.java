package pizzaioli.production.domain.models;

import java.time.LocalDateTime;

public class ProductType {
    private Integer id;
    private String name;
    private boolean active;
    private LocalDateTime createdDate;

    public ProductType(Integer id, String name, boolean active, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdDate = createdDate;
    }

    public ProductType() {
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
