package mate.academy.springboot.dto.mapper;

import lombok.RequiredArgsConstructor;
import mate.academy.springboot.dto.ProductRequestDto;
import mate.academy.springboot.dto.ProductResponseDto;
import mate.academy.springboot.model.Product;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements RequestDtoMapper<ProductRequestDto,
        Product>, ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public Product toModel(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setTitle(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        return productResponseDto;
    }
}
