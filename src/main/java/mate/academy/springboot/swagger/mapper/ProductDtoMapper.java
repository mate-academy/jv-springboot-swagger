package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper implements DtoMapper<Product,
        ProductRequestDto, ProductResponseDto> {
    private final ProductService productService;

    public ProductDtoMapper(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Product toModel(ProductRequestDto requestDto) {
        return new Product(requestDto.getTitle(), requestDto.getPrice());
    }

    @Override
    public ProductResponseDto toDto(Product model) {
        return new ProductResponseDto(model.getId(), model.getTitle(), model.getPrice());
    }
}
