package mate.academy.springboot.swagger.mapper.impl;

import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<ProductRequestDto, ProductResponseDto, Product> {
    @Override
    public Product mapToModel(ProductRequestDto dto) {
        return new Product(dto.getTitle(), dto.getPrice());
    }

    @Override
    public ProductResponseDto mapToDto(Product product) {
        return new ProductResponseDto(product.getId(),
                product.getTitle(),
                product.getPrice());
    }
}
