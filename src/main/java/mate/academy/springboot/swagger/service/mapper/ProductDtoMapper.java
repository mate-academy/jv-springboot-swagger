package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductDtoMapper implements DtoMapper<Product,
        ProductResponseDto,
        ProductRequestDto> {
    @Override
    public abstract Product toModel(ProductRequestDto dto);

    @Override
    public abstract ProductResponseDto toDto(Product entity);
}
