package mate.academy.springboot.swagger.mapper.impl;

import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestDtoMapperImpl implements RequestDtoMapper<ProductRequestDto, Product> {
    @Override
    public Product toModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }
}
