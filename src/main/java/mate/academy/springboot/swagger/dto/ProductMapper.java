package mate.academy.springboot.swagger.dto;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements ResponseDtoMapper<ProductResponseDto, Product>,
        RequestDtoMapper<ProductRequestDto, Product> {
    @Override
    public Product toModel(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setPrice(productRequestDto.getPrice());
        product.setTitle(productRequestDto.getTitle());
        return product;
    }

    @Override
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setTitle(product.getTitle());
        return productResponseDto;
    }
}
