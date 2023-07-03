package mate.academy.springboot.swagger.mapper.request;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.mapper.RequestMapper;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestMapper implements RequestMapper<Product, ProductRequestDto> {
    @Override
    public Product toModel(ProductRequestDto productRequestDto) {
        return new Product()
                .setTitle(productRequestDto.getTitle())
                .setPrice(productRequestDto.getPrice());
    }
}
