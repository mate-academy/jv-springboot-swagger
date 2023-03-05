package mate.academy.springboot.swagger.model.dto;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(product.getId(),
                                          product.getTitle(),
                                          product.getPrice());
    }

    public Product toModel(ProductResponseDto productResponseDto) {
        return new Product(productResponseDto.getId(),
                               productResponseDto.getTitle(),
                               productResponseDto.getPrice());
    }
}
