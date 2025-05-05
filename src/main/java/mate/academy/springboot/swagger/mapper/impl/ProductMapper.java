package mate.academy.springboot.swagger.mapper.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.mapper.Mapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements Mapper<Product, ProductRequestDto, ProductResponseDto> {
    @Override
    public Product toModel(ProductRequestDto dto) {
        return new Product(
                dto.getTitle(),
                dto.getPrice()
        );
    }

    @Override
    public ProductResponseDto toDto(Product model) {
        return new ProductResponseDto(
                model.getId(),
                model.getTitle(),
                model.getPrice());
    }
}
