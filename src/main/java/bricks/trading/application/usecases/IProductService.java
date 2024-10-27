package bricks.trading.application.usecases;

import bricks.trading.application.dto.ProductCreateDto;
import bricks.trading.application.dto.ProductResponseDto;
import bricks.trading.domain.model.ProductFilter;

import java.util.List;

public interface IProductService {

    List<ProductResponseDto> findAll(ProductFilter filter, int page, int size);
    ProductResponseDto findById(Integer id);
    ProductResponseDto create(ProductCreateDto productCreateDto);
    void deleteById(Integer id);
    ProductResponseDto update(Integer id, ProductCreateDto productCreateDto);
}
