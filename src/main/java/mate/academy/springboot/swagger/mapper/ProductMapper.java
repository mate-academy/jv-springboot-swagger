package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(product.getId(), product.getTitle(), product.getPrice());
    }

    public Product toModel(ProductRequestDto dto) {
        return new Product(null, dto.getTitle(), dto.getPrice());
    }
}
