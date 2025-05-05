package mate.academy.springboot.swagger.mapper.response;

import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper implements ResponseMapper<Product, ProductResponseDto> {
    @Override
    public ProductResponseDto toDto(Product product) {
        return new ProductResponseDto()
                .setId(product.getId())
                .setTitle(product.getTitle())
                .setPrice(product.getPrice());
    }
}
