package mate.academy.springboot.swagger.mapper.impl;

import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseDtoMapper implements ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setTitle(product.getTitle());
        return dto;
    }
}
