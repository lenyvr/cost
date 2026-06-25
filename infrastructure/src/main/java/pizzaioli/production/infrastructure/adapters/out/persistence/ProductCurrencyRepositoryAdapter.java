package pizzaioli.production.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;
import pizzaioli.production.domain.models.ProductCurrency;
import pizzaioli.production.domain.ports.output.ProductCurrencyRepositorySPI;
import pizzaioli.production.infrastructure.adapters.out.persistence.entity.ProductCurrencyEntity;
import pizzaioli.production.infrastructure.adapters.out.persistence.repository.ProductCurrencyJpaRepository;
import pizzaioli.production.infrastructure.mapper.ProductCurrencyMapper;

import java.util.Optional;

@Component
public class ProductCurrencyRepositoryAdapter implements ProductCurrencyRepositorySPI {

    private final ProductCurrencyJpaRepository jpaRepository;

    public ProductCurrencyRepositoryAdapter(ProductCurrencyJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ProductCurrency save(ProductCurrency productCurrency) {
        ProductCurrencyEntity savedEntity = jpaRepository.save(ProductCurrencyMapper.toEntity(productCurrency));
        return ProductCurrencyMapper.toDomainFromEntity(savedEntity);
    }

    @Override
    public ProductCurrency getByCode(String code) {
        Optional<ProductCurrencyEntity> productCurrencyEntityFound = jpaRepository.findByCode(code);
        return productCurrencyEntityFound.map(ProductCurrencyMapper::toDomainFromEntity).orElse(null);
    }
}
