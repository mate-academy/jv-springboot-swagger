package mate.academy.springboot.swagger.mapper.response;

import mate.academy.springboot.swagger.mapper.RequestMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestMapper implements RequestMapper<Product, ProductRequestDto> {
    @Override
    public Product toModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }
}
