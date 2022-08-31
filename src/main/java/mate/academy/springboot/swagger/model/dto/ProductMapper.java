package mate.academy.springboot.swagger.model.dto;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setTitle(product.getTitle());
        responseDto.setPrice(product.getPrice());
        return responseDto;
    }

    public Product toModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setPrice(requestDto.getPrice());
        product.setTitle(requestDto.getTitle());
        return product;
    }
}
