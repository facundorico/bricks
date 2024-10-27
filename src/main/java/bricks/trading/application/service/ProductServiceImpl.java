package bricks.trading.application.service;

import bricks.trading.adapters.exception.CategoryNotFoundException;
import bricks.trading.adapters.exception.RecordNotFoundException;
import bricks.trading.adapters.mapper.ProductConverter;
import bricks.trading.adapters.outbound.externalservice.business.BusinessServiceImpl;
import bricks.trading.application.constant.Messages;
import bricks.trading.application.dto.CategoryResponseDto;
import bricks.trading.application.dto.ProductCreateDto;
import bricks.trading.application.dto.ProductResponseDto;
import bricks.trading.application.usecases.IBusinessService;
import bricks.trading.application.usecases.IProductService;
import bricks.trading.domain.model.Product;
import bricks.trading.domain.model.ProductFilter;
import bricks.trading.domain.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ProductConverter converter;
    private final IBusinessService businessService;

    @Override
    @Transactional()
    public List<ProductResponseDto> findAll(ProductFilter filter, int page, int size) {
        List<Product> productList = productRepository.findAll(filter, page, size);
        return productList.stream()
                .map(product -> (ProductResponseDto) converter.map(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public ProductResponseDto findById(Integer id) {
        return converter.toProductResponseDto(productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format(Messages.PRODUCT_NOT_FOUND_BY_ID, id))));
    }

    @Override
    public ProductResponseDto create(ProductCreateDto productCreateDto) {
        List<CategoryResponseDto> categoriesList = businessService.getAllCategories();

        boolean exists = categoriesList.stream()
                .anyMatch(category -> category.getCode().equalsIgnoreCase(productCreateDto.getCategory()));

        if (Boolean.FALSE.equals(exists)) {
            throw new CategoryNotFoundException(String.format(Messages.CATEGORY_NOT_FOUND_BY_CODE, productCreateDto.getCategory()));
        }

        Product product = converter.toModel(productCreateDto);
        return converter.toProductResponseDto(productRepository.create(product));
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDto update(Integer id, ProductCreateDto productCreateDto) {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format(Messages.PRODUCT_NOT_FOUND_BY_ID, id)));
        productToUpdate.setName(productCreateDto.getName());
        productToUpdate.setStock(productCreateDto.getStock());
        productToUpdate.setPrice(productCreateDto.getPrice());
        productToUpdate.setCategory(productCreateDto.getCategory());

        Product product = converter.toModel(productToUpdate);
        return converter.toProductResponseDto(productRepository.create(product));
    }
}
