package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.config.MapperConfig;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProductDtoMapper extends DtoMapper<ProductRequestDto, Product,
        ProductResponseDto> {
}
