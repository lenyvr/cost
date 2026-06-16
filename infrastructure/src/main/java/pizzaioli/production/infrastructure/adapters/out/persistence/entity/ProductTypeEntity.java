package pizzaioli.production.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Boolean active;

    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;
}
