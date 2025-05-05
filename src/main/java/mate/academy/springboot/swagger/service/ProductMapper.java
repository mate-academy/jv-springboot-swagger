package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;

public interface ProductMapper {
    Product mapToModel(ProductRequestDto dto);

    ProductResponseDto mapToDto(Product product);
}
