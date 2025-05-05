package mate.academy.springboot.swagger.dto;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toModel(ProductRequestDto receiveDto) {
        return new Product(
                receiveDto.getTitle(),
                receiveDto.getPrice());
    }

    public ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getTitle(),
                product.getPrice());
    }
}
