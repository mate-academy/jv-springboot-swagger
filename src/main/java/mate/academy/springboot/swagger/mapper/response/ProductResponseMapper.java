package mate.academy.springboot.swagger.mapper.response;

import mate.academy.springboot.swagger.mapper.DtoResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper implements DtoResponseMapper<ProductResponseDto, Product> {

    @Override
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        return productResponseDto;
    }
}
