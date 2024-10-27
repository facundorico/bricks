package bricks.trading.adapters.mapper;

import bricks.trading.adapters.outbound.persistence.jpa.entity.ProductEntity;
import bricks.trading.application.dto.ProductResponseDto;
import bricks.trading.domain.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductConverter extends ModelConverter {

    public Product toModel(Object data) { return (Product) this.map(data, Product.class);}

    public ProductEntity toEntity(Object data) { return (ProductEntity) this.map(data, ProductEntity.class);}
    public ProductResponseDto toProductResponseDto(Object data) {return (ProductResponseDto) this.map(data, ProductResponseDto.class);}

    //public ProductFilter filterToModel(ProductFilterDto dto) { return (ProductFilter) map(dto, bricks.trading.domain.model.ProductFilter.class);}

}
