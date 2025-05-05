package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements
        GenericMapper<Product, ProductRequestDto, ProductResponseDto> {
    @Override
    public Product mapToModel(ProductRequestDto productRequestDto) {
        return new Product()
                .setTitle(productRequestDto.getTitle())
                .setPrice(productRequestDto.getPrice());
    }

    @Override
    public ProductResponseDto mapToDto(Product product) {
        return new ProductResponseDto()
                .setId(product.getId())
                .setTitle(product.getTitle())
                .setPrice(product.getPrice());
    }
}
