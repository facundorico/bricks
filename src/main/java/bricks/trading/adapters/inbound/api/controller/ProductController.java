package bricks.trading.adapters.inbound.api.controller;

import bricks.trading.adapters.inbound.api.utils.Paths;
import bricks.trading.application.dto.ApiResponseDto;
import bricks.trading.application.dto.ProductCreateDto;
import bricks.trading.application.dto.ProductResponseDto;
import bricks.trading.application.usecases.IProductService;
import bricks.trading.domain.model.ProductFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bricks.trading.application.constant.Messages.PRODUCT_CREATED_SUCCESSFUL;
import static bricks.trading.application.constant.Messages.PRODUCT_DELETED_SUCCESSFUL;
import static bricks.trading.application.constant.Messages.PRODUCT_GET_SUCCESSFUL;
import static bricks.trading.application.constant.Messages.PRODUCT_UPDATED_SUCCESSFUL;

@RestController
@RequestMapping(Paths.PRODUCT_ENDPOINT)
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> findAll(ProductFilter filter, @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size){
        List<ProductResponseDto> responseDtoList = productService.findAll(filter, page, size);
        ApiResponseDto<List<ProductResponseDto>> response = ApiResponseDto.<List<ProductResponseDto>>builder()
                .ok(Boolean.TRUE)
                .message(PRODUCT_GET_SUCCESSFUL)
                .body(responseDtoList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> findById(@PathVariable("id") Integer id) {
        ProductResponseDto responseDto = productService.findById(id);
        ApiResponseDto<ProductResponseDto> response = ApiResponseDto.<ProductResponseDto>builder()
                .ok(Boolean.TRUE)
                .message(PRODUCT_GET_SUCCESSFUL)
                .body(responseDto)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> create(@RequestBody @Valid ProductCreateDto productCreateDto) {
        ProductResponseDto productSaved = productService.create(productCreateDto);
        ApiResponseDto<ProductResponseDto> response = ApiResponseDto.<ProductResponseDto>builder()
                .ok(Boolean.TRUE)
                .message(PRODUCT_CREATED_SUCCESSFUL)
                .body(productSaved)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteById(@PathVariable("id") Integer id) {
        productService.findById(id);
        productService.deleteById(id);
        ApiResponseDto<Void> response = ApiResponseDto.<Void>builder()
                .ok(Boolean.TRUE)
                .message(PRODUCT_DELETED_SUCCESSFUL)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> update(@PathVariable("id") Integer id, @Valid @RequestBody ProductCreateDto productUpdateDto) {
        productService.findById(id);
        ProductResponseDto productUpdated = productService.update(id, productUpdateDto);
        ApiResponseDto<ProductResponseDto> response = ApiResponseDto.<ProductResponseDto>builder()
                .ok(Boolean.TRUE)
                .message(PRODUCT_UPDATED_SUCCESSFUL)
                .body(productUpdated)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
