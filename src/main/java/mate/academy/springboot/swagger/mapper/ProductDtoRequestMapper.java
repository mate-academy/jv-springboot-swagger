package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoRequestMapper {
    public Product fromDto(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }
}
