package mate.academy.springboot.swagger.mapper;

import java.util.List;
import mate.academy.springboot.swagger.dto.ProductDto;
import mate.academy.springboot.swagger.model.Product;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Product toModel(ProductDto dto);

    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> product);

    List<Product> toModelList(List<ProductDto> productDto);
}
