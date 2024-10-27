package bricks.trading.application.service;

import bricks.trading.adapters.exception.CategoryNotFoundException;
import bricks.trading.adapters.exception.RecordNotFoundException;
import bricks.trading.adapters.mapper.ProductConverter;
import bricks.trading.application.constant.Messages;
import bricks.trading.application.dto.ProductCreateDto;
import bricks.trading.application.dto.ProductResponseDto;
import bricks.trading.application.logging.ILoggerUtil;
import bricks.trading.domain.model.Product;
import bricks.trading.domain.model.ProductFilter;
import bricks.trading.domain.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ProductConverter converter;

    @Mock
    private ILoggerUtil logger;

    @InjectMocks
    private ProductServiceImpl productService;

    private static final String PRODUCT_SERVICE = "ProductServiceImpl";

    @Test
    void testFindAllSuccess() {
        ProductFilter filter = new ProductFilter();
        int page = 0;
        int size = 10;
        List<Product> productList = List.of(new Product(1, "Product1", BigDecimal.valueOf(20.0), 10, "Category1"), new Product(2, "Product2", BigDecimal.valueOf(20.0), 10, "Category1"));
        List<ProductResponseDto> responseDtoList = List.of(new ProductResponseDto(1, "Product1", BigDecimal.valueOf(20.0), 10, "Category1"), new ProductResponseDto(2, "Product2", BigDecimal.valueOf(20.0), 10, "Category1"));

        Mockito.when(productRepository.findAll(filter, page, size)).thenReturn(productList);
        Mockito.when(converter.map(Mockito.any(Product.class), Mockito.eq(ProductResponseDto.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getCategory());
                });

        List<ProductResponseDto> result = productService.findAll(filter, page, size);

        assertEquals(responseDtoList, result);
        Mockito.verify(logger).info(PRODUCT_SERVICE, "findAll");
        Mockito.verify(productRepository).findAll(filter, page, size);
    }

    @Test
    void testFindByIdSuccess() {
        int productId = 1;
        Product product =new Product(1, "Product1", BigDecimal.valueOf(20.0), 10, "Category1");
        ProductResponseDto responseDto = new ProductResponseDto(productId, "Product1", BigDecimal.valueOf(20.0), 10, "Category1");

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(converter.toProductResponseDto(product)).thenReturn(responseDto);

        ProductResponseDto result = productService.findById(productId);

        assertEquals(responseDto, result);
        Mockito.verify(logger).info(PRODUCT_SERVICE, "findById");
    }

    @Test
    void testFindByIdNotFound() {
        int productId = 1;
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            productService.findById(productId);
        });

        assertEquals(String.format(Messages.PRODUCT_NOT_FOUND_BY_ID, productId), exception.getMessage());
        Mockito.verify(logger).info(PRODUCT_SERVICE, "findById");
    }

    @Test
    void testDeleteById() {
        int productId = 1;

        Mockito.doNothing().when(productRepository).deleteById(productId);

        productService.deleteById(productId);

        Mockito.verify(logger).info(PRODUCT_SERVICE, "deleteById");
        Mockito.verify(productRepository).deleteById(productId);
    }

}
