package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<Product, ProductResponseDto, ProductRequestDto> {

    @Override
    public Product mapToModel(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setPrice(productRequestDto.getPrice());
        product.setTitle(productRequestDto.getTitle());
        return product;
    }

    @Override
    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto responseProductDto = new ProductResponseDto();
        responseProductDto.setId(product.getId());
        responseProductDto.setTitle(product.getTitle());
        responseProductDto.setPrice(product.getPrice());
        return responseProductDto;
    }
}
