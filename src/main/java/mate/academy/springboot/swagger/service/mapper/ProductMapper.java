package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper implements RequestDtoMapper<ProductRequestDto, Product>,
                                ResponseDtoMapper<Product, ProductResponseDto> {
    @Override
    public Product toModel(ProductRequestDto dto) {
        Product model = new Product();
        model.setPrice(dto.getPrice());
        model.setTitle(dto.getTitle());
        return model;
    }

    @Override
    public ProductResponseDto toDto(Product model) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(model.getId());
        dto.setPrice(model.getPrice());
        dto.setTitle(model.getTitle());
        return dto;
    }
}
