package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product fromDto(ProductRequestDto object) {
        Product product = new Product();
        product.setTitle(object.getTitle());
        product.setPrice(object.getPrice());
        return product;
    }

    public ProductResponseDto toDto(Product object) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setTitle(object.getTitle());
        responseDto.setPrice(object.getPrice());
        responseDto.setId(object.getId());
        return responseDto;
    }

}
