package bricks.trading.adapters.outbound.persistence.repository;

import bricks.trading.adapters.mapper.ProductConverter;
import bricks.trading.adapters.outbound.persistence.jpa.IProductJpaRepository;
import bricks.trading.adapters.outbound.persistence.jpa.entity.ProductEntity;
import bricks.trading.adapters.outbound.persistence.jpa.specs.ProductSpecification;
import bricks.trading.domain.model.Product;
import bricks.trading.domain.model.ProductFilter;
import bricks.trading.domain.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements IProductRepository {

    private final IProductJpaRepository jpaRepository;
    private final ProductConverter converter;

    @Override
    public List<Product> findAll(ProductFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Specification<ProductEntity>> specificationList = new ArrayList<>();
        if(Objects.nonNull(filter.getName())) {
            specificationList.add(ProductSpecification.nameIsEqualTo(filter.getName()));
        }
        if(Objects.nonNull(filter.getPrice())) {
            specificationList.add(ProductSpecification.priceIsEqualTo(filter.getPrice()));
        }
        if(Objects.nonNull(filter.getStock())) {
            specificationList.add(ProductSpecification.stockIsEqualTo(filter.getStock()));
        }
        if(Objects.nonNull(filter.getCategory())) {
            specificationList.add(ProductSpecification.categoryIsEqualTo(filter.getCategory()));
        }
        Page<ProductEntity> products = jpaRepository.findAll(Specification.allOf(specificationList), pageable);
        List<ProductEntity> productsList = products.getContent();
        return productsList.stream().map(this.converter::toModel).toList();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return jpaRepository.findById(id).map(this.converter::toModel).or(Optional::empty);
    }

    @Override
    @Transactional
    public Product create(Product product) {
        ProductEntity productSaved = jpaRepository.save(converter.toEntity(product));
        return converter.toModel(jpaRepository.save(productSaved));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }
}
