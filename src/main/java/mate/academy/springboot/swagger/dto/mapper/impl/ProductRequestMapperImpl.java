package mate.academy.springboot.swagger.dto.mapper.impl;

import mate.academy.springboot.swagger.dto.mapper.RequestMapperDto;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestMapperImpl implements RequestMapperDto<ProductRequestDto, Product> {
    @Override
    public Product toModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }
}
