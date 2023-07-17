package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoResponseMapper {

    public ProductResponseDto toDto(Product object) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(object.getId());
        productResponseDto.setTitle(object.getTitle());
        productResponseDto.setPrice(object.getPrice());
        return productResponseDto;
    }
}
