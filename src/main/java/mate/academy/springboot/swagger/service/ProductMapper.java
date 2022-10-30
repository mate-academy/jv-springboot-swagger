package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;

public interface ProductMapper {
    Product dtoToModel(ProductRequestDto productRequestDto);

    ProductResponseDto modelToDto(Product product);
}
