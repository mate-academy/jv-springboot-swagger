package mate.academy.springboot.swagger.mapper;

import java.util.List;
import mate.academy.springboot.swagger.dto.ProductDto;
import mate.academy.springboot.swagger.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    Product toModel(ProductDto dto);

    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> product);

    List<Product> toModelList(List<ProductDto> productDto);
}
