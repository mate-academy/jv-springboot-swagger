package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.product.ProductRequestDto;
import mate.academy.springboot.swagger.dto.product.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements RequestDtoMapper<ProductRequestDto, Product>,
                    ResponseDtoMapper<ProductResponseDto, Product> {

    @Override
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto mapToDto(Product model) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(model.getId());
        productResponseDto.setTitle(model.getTitle());
        productResponseDto.setPrice(model.getPrice());
        return productResponseDto;
    }
}
