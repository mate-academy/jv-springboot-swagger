package mate.academy.springboot.swagger.mapper.impl;

import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseDtoMapper implements ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public ProductResponseDto toDto(Product entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }
}
