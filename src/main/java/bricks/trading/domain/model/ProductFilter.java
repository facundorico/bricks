package bricks.trading.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilter {

    private String name;
    private BigDecimal price;
    private Integer stock;
    private String category;
}
