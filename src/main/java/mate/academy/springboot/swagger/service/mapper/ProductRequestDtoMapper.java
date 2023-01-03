package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestDtoMapper implements RequestDtoMapper<ProductRequestDto, Product> {

    @Override
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setPrice(dto.getPrice());
        product.setTitle(dto.getTitle());
        return product;
    }
}
