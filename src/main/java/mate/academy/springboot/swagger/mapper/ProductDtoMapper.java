package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper implements DtoMapper<Product,
        ProductRequestDto, ProductResponseDto> {

    @Override
    public Product toModel(ProductRequestDto requestDto) {
        return new Product(requestDto.getTitle(), requestDto.getPrice());
    }

    @Override
    public ProductResponseDto toDto(Product model) {
        return new ProductResponseDto(model.getId(), model.getTitle(), model.getPrice());
    }
}
