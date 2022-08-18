package mate.academy.springboot.swagger.dto.mapper.impl;

import mate.academy.springboot.swagger.dto.mapper.ResponseMapperDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapperImpl implements ResponseMapperDto<ProductResponseDto, Product> {
    @Override
    public ProductResponseDto toDto(Product model) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(model.getId());
        responseDto.setTitle(model.getTitle());
        responseDto.setPrice(model.getPrice());
        return responseDto;
    }
}
