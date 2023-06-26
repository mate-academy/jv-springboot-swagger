package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper extends DtoMapper<Product,
        ProductResponseDto,
        ProductRequestDto> {
    @Override
    Product toModel(ProductRequestDto dto);

    @Override
    ProductResponseDto toDto(Product entity);
}
