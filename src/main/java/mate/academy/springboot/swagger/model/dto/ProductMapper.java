package mate.academy.springboot.swagger.model.dto;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(product.getId(),
                                          product.getTitle(),
                                          product.getPrice());
    }

    public Product toModel(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setPrice(productRequestDto.getPrice());
        product.setTitle(productRequestDto.getTitle());
        return product;
    }
}
