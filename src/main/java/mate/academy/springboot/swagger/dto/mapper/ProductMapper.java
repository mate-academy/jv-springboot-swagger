package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto rDto = new ProductResponseDto();
        rDto.setId(product.getId());
        rDto.setTitle(product.getTitle());
        rDto.setPrice(product.getPrice());
        return rDto;
    }

    public Product toModel(ProductRequestDto pRDto) {
        Product product = new Product();
        product.setTitle(pRDto.getTitle());
        product.setPrice(pRDto.getPrice());
        return product;
    }
}
