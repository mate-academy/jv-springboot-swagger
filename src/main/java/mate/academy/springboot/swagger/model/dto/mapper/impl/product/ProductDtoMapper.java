package mate.academy.springboot.swagger.model.dto.mapper.impl.product;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.model.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper implements RequestDtoMapper<ProductRequestDto, Product>,
        ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public Product mapToModel(ProductRequestDto dto, Long id) {
        return Product.builder()
                .id(id)
                .title(dto.getTitle())
                .price(dto.getPrice())
                .build();
    }

    @Override
    public ProductResponseDto mapToDto(Product model) {
        return ProductResponseDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .price(model.getPrice())
                .build();
    }
}
