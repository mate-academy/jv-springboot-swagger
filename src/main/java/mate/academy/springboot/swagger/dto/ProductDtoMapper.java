package mate.academy.springboot.swagger.dto;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public Product toModel(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setTitle(productRequestDto.getTitle());
        product.setPrice(new BigDecimal(productRequestDto.getPrice()));
        return product;
    }

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice().toString());
        return productResponseDto;
    }
}
