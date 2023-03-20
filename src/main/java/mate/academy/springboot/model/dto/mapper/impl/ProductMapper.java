package mate.academy.springboot.model.dto.mapper.impl;

import mate.academy.springboot.model.Product;
import mate.academy.springboot.model.dto.ProductRequestDto;
import mate.academy.springboot.model.dto.ProductResponseDto;
import mate.academy.springboot.model.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.model.dto.mapper.ResponseDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements RequestDtoMapper<ProductRequestDto,
        Product>, ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public Product toModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto toDto(Product model) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(model.getId());
        productResponseDto.setTitle(model.getTitle());
        productResponseDto.setPrice(model.getPrice());
        return productResponseDto;
    }
}
