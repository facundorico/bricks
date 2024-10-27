package bricks.trading.adapters.inbound.api.controller;

import bricks.trading.application.dto.ApiResponseDto;
import bricks.trading.application.dto.ProductCreateDto;
import bricks.trading.application.dto.ProductResponseDto;
import bricks.trading.application.usecases.IProductService;
import bricks.trading.domain.model.ProductFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static bricks.trading.application.constant.Messages.PRODUCT_CREATED_SUCCESSFUL;
import static bricks.trading.application.constant.Messages.PRODUCT_DELETED_SUCCESSFUL;
import static bricks.trading.application.constant.Messages.PRODUCT_GET_SUCCESSFUL;
import static bricks.trading.application.constant.Messages.PRODUCT_UPDATED_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;


    @Test
    @DisplayName("findAll - Should return all products successfully test")
    void testFindAll() {
        ProductFilter filter = new ProductFilter();
        List<ProductResponseDto> products = List.of(new ProductResponseDto(1, "Product 1", BigDecimal.valueOf(10), 1, "CAT"), new ProductResponseDto(2, "Product 2", BigDecimal.valueOf(10), 1, "CAT"));

        Mockito.when(productService.findAll(filter, 0, 10)).thenReturn(products);

        ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> responseEntity = productController.findAll(filter, 0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(PRODUCT_GET_SUCCESSFUL, responseEntity.getBody().getMessage());
        assertEquals(products, responseEntity.getBody().getBody());
        assertEquals(Boolean.TRUE, responseEntity.getBody().isOk());

        Mockito.verify(productService, Mockito.times(1)).findAll(filter, 0, 10);
    }

    @Test
    @DisplayName("testFindById - Should return a specific products by id successfully test")
    void testFindById() {
        Integer id = 1;
        ProductResponseDto product = new ProductResponseDto(id, "Product 1", BigDecimal.valueOf(10), 1, "CAT");

        Mockito.when(productService.findById(id)).thenReturn(product);

        ResponseEntity<ApiResponseDto<ProductResponseDto>> responseEntity = productController.findById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(PRODUCT_GET_SUCCESSFUL, responseEntity.getBody().getMessage());
        assertEquals(product, responseEntity.getBody().getBody());
        assertEquals(Boolean.TRUE, responseEntity.getBody().isOk());

        Mockito.verify(productService, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("testCreate - Should create product successfully test")
    void testCreate() {
        ProductCreateDto productCreateDto = new ProductCreateDto("Product 1", BigDecimal.valueOf(10), 1, "CAT");
        ProductResponseDto createdProduct = new ProductResponseDto(1, "Product 1", BigDecimal.valueOf(10), 1, "CAT");

        Mockito.when(productService.create(productCreateDto)).thenReturn(createdProduct);

        ResponseEntity<ApiResponseDto<ProductResponseDto>> responseEntity = productController.create(productCreateDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(PRODUCT_CREATED_SUCCESSFUL, responseEntity.getBody().getMessage());
        assertEquals(createdProduct, responseEntity.getBody().getBody());
        assertEquals(Boolean.TRUE, responseEntity.getBody().isOk());

        Mockito.verify(productService, Mockito.times(1)).create(productCreateDto);
    }

    @Test
    @DisplayName("testDeleteById - Should delete a product by id successfully test")
    void testDeleteById() {
        Integer id = 1;

        Mockito.doNothing().when(productService).deleteById(id);

        ResponseEntity<ApiResponseDto<Void>> responseEntity = productController.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(PRODUCT_DELETED_SUCCESSFUL, responseEntity.getBody().getMessage());
        assertEquals(Boolean.TRUE, responseEntity.getBody().isOk());

        Mockito.verify(productService, Mockito.times(1)).findById(id);
        Mockito.verify(productService, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("testUpdate - Should update a product by id successfully test")
    void testUpdate() {
        Integer id = 1;
        ProductCreateDto productUpdateDto = new ProductCreateDto("Product 1", BigDecimal.valueOf(10), 1, "CAT");
        ProductResponseDto updatedProduct = new ProductResponseDto(id, "Product 1", BigDecimal.valueOf(10), 1, "CAT");

        Mockito.when(productService.update(id, productUpdateDto)).thenReturn(updatedProduct);

        ResponseEntity<ApiResponseDto<ProductResponseDto>> responseEntity = productController.update(id, productUpdateDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(PRODUCT_UPDATED_SUCCESSFUL, responseEntity.getBody().getMessage());
        assertEquals(updatedProduct, responseEntity.getBody().getBody());
        assertEquals(Boolean.TRUE, responseEntity.getBody().isOk());

        Mockito.verify(productService, Mockito.times(1)).findById(id);
        Mockito.verify(productService, Mockito.times(1)).update(id, productUpdateDto);
    }

}
