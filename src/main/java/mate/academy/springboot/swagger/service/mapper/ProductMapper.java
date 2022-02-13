package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product mapToModel(ProductRequestDto dto) {
        return new Product(dto.getTitle(), dto.getPrice());
    }

    public ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(product.getId(),
                product.getTitle(), product.getPrice());
    }
}
