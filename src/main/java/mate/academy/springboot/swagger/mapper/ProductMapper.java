package mate.academy.springboot.swagger.mapper;

import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements DtoRequestMapper<ProductRequestDto, Product>,
                                      DtoResponseMapper<ProductResponseDto, Product> {

    @Override
    public Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
