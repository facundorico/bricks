package bricks.trading.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCreateDto {

    @NotNull(message = "Product name cannot be null")
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "the price value cannot be 0.0 or less")
    private BigDecimal price;
    @DecimalMin(value = "0", message = "the value of stock cannot be less than 0")
    private Integer stock;
    private String category;
}
