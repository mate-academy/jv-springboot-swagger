package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;

public interface ProductMapper {
    ProductResponseDto toDto(Product product);

    Product toModel(ProductRequestDto productRequestDto);
}
