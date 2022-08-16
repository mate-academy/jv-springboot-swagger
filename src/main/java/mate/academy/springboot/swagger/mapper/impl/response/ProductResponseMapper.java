package mate.academy.springboot.swagger.mapper.impl.response;

import mate.academy.springboot.swagger.mapper.DtoResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper implements DtoResponseMapper<ProductResponseDto, Product> {
    @Override
    public ProductResponseDto toDto(Product model) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setPrice(model.getPrice());
        return dto;
    }
}
