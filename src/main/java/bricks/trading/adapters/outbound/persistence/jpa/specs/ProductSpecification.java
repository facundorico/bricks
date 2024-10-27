package bricks.trading.adapters.outbound.persistence.jpa.specs;

import bricks.trading.adapters.outbound.persistence.jpa.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    private ProductSpecification() {}

    public static Specification<ProductEntity> nameIsEqualTo(String name) {
        return (product, query, builder) -> builder.equal(product.get("name"), name);
    }

    public static Specification<ProductEntity> priceIsEqualTo(BigDecimal price) {
        return (product, query, builder) -> builder.equal(product.get("price"), price);
    }

    public static Specification<ProductEntity> stockIsEqualTo(Integer stock) {
        return (product, query, builder) -> builder.equal(product.get("stock"), stock);
    }

    public static Specification<ProductEntity> categoryIsEqualTo(String category) {
        return (product, query, builder) -> builder.equal(product.get("category"), category);
    }
}
