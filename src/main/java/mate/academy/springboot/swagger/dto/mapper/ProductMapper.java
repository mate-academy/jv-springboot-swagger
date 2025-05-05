package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.responce.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(product.getId(),
                product.getTitle(), product.getPrice());
    }

    public Product toModel(ProductRequestDto requestDto) {
        return new Product(requestDto.getTitle(), requestDto.getPrice());
    }
}
