package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;

public interface MapperDto {
    ProductResponseDto toDto(Product product);

    Product toModel(ProductRequestDto productRequestDto);
}
