package mate.academy.springboot.swagger.sevice.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<Product, ProductResponseDto, ProductRequestDto> {
    @Override
    public ProductResponseDto toDto(Product entity) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setPrice(entity.getPrice());
        responseDto.setTitle(entity.getTitle());
        return responseDto;
    }

    @Override
    public Product toEntity(ProductRequestDto request) {
        Product product = new Product();
        product.setPrice(request.getPrice());
        product.setTitle(request.getTitle());
        return product;
    }
}
