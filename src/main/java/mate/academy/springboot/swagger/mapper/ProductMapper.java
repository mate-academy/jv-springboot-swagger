package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;


    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toModel(ProductRequestDto dto) {
        return modelMapper.map(dto, Product.class);
    }

    public ProductResponseDto toDto(Product product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }
}
