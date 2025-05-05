package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.config.MapperConfig;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {
    ProductResponseDto mapToDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product mapToEntity(ProductRequestDto productRequestDto);
}
