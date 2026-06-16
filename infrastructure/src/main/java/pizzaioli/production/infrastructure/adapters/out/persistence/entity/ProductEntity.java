package pizzaioli.production.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "amount_value")
    private Double amountValue;

    @Column(name = "measurement_unit_code")
    private String measurementUnitCode;

    @Column(name = "product_currency_id")
    private Long productCurrencyId;

    @Column(name = "product_type_id")
    private Long productTypeId;

    private Boolean active;

    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;
}
