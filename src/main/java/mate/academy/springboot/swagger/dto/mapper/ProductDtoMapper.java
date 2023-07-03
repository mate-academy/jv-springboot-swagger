package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public ProductResponseDto modelToDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setTitle(product.getTitle());
        responseDto.setId(product.getId());
        responseDto.setPrice(product.getPrice());
        return responseDto;
    }

    public Product dtoToModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return product;
    }
}
