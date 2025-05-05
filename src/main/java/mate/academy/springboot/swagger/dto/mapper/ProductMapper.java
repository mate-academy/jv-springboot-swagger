package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements MapperDto<ProductRequestDto, ProductResponseDto, Product> {
    @Override
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        return productResponseDto;
    }

    @Override
    public Product toModel(ProductRequestDto dtoRequest) {
        Product product = new Product();
        product.setTitle(dtoRequest.getTitle());
        product.setPrice(dtoRequest.getPrice());
        return product;
    }
}
