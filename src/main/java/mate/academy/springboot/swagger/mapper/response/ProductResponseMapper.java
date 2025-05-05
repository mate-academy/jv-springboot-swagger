package mate.academy.springboot.swagger.mapper.response;

import mate.academy.springboot.swagger.mapper.ResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper implements ResponseMapper<ProductResponseDto, Product> {
    @Override
    public ProductResponseDto toDto(Product model) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setPrice(model.getPrice());
        return dto;
    }
}
