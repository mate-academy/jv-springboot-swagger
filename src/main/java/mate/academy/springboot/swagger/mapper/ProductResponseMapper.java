package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper implements DtoResponseMapper<Product, ProductResponseDto> {
    @Override
    public ProductResponseDto toDto(Product model) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(model.getId());
        productResponseDto.setTitle(model.getTitle());
        productResponseDto.setPrice(model.getPrice());
        return productResponseDto;
    }
}
