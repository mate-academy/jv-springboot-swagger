package service.mapper;

import dto.ProductRequestDto;
import dto.ProductResponseDto;
import model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public Product mapToModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setPrice(requestDto.getPrice());
        product.setTitle(requestDto.getTitle());
        return product;
    }

    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setPrice(product.getPrice());
        responseDto.setTitle(product.getTitle());
        return responseDto;
    }
}
