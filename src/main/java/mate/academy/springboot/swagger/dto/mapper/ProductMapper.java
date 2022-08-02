package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTittle());
        product.setPrice(requestDto.getPrice());
        return product;
    }

    public ProductResponseDto toResponseDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setTittle(product.getTitle());
        responseDto.setPrice(product.getPrice());
        return responseDto;
    }
}
