package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements RequestDtoMapper<ProductRequestDto, Product>,
        ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public Product mapToModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto mapToDto(Product model) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(model.getId());
        responseDto.setTitle(model.getTitle());
        responseDto.setPrice(model.getPrice());
        return responseDto;
    }
}
