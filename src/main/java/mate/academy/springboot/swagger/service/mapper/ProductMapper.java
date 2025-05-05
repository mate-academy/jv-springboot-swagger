package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.domain.Product;
import mate.academy.springboot.swagger.domain.dto.ProductRequestDto;
import mate.academy.springboot.swagger.domain.dto.ProductResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toDomain(ProductRequestDto dto) {
        return modelMapper.map(dto, Product.class);
    }

    public ProductResponseDto toDto(Product product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }
}
