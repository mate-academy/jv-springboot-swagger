package mate.academy.springboot.swagger.service.mapper.impl;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<ProductResponseDto, ProductRequestDto, Product> {
    @Override
    public ProductResponseDto mapToDto(Product model) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(model.getId());
        responseDto.setTitle(model.getTitle());
        responseDto.setPrice(model.getPrice());
        return responseDto;
    }

    @Override
    public Product mapToModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setPrice(requestDto.getPrice());
        product.setTitle(requestDto.getTitle());
        return product;
    }
}
