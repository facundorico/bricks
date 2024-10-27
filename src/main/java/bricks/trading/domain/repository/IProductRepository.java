package bricks.trading.domain.repository;

import bricks.trading.domain.model.Product;
import bricks.trading.domain.model.ProductFilter;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {

    List<Product> findAll(ProductFilter filter, int page, int size);
    Optional<Product> findById(Integer id);
    Product create(Product product);
    void deleteById(Integer id);

}
