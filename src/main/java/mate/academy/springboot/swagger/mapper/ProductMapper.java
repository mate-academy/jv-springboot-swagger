package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.dto.ProductDtoRequest;
import mate.academy.springboot.swagger.dto.ProductDtoResponse;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDtoResponse mapToDto(Product product) {
        return new ProductDtoResponse()
            .setTitle(product.getTitle())
            .setPrice(product.getPrice());
    }

    public Product mapToEntity(ProductDtoRequest dto) {
        return new Product()
            .setTitle(dto.getTitle())
            .setPrice(dto.getPrice());
    }
}
