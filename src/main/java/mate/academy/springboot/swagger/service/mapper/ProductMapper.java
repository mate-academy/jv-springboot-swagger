package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setPrice(dto.getPrice());
        product.setTitle(dto.getTitle());
        return product;
    }

    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setTitle(product.getTitle());
        return dto;
    }
}
