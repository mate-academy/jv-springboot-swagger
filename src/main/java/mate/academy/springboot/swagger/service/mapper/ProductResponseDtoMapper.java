package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseDtoMapper implements ResponseDtoMapper<ProductResponseDto, Product> {

    @Override
    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setTitle(product.getTitle());
        return dto;
    }
}
