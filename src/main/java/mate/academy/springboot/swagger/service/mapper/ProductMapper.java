package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public Product toModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }
}
