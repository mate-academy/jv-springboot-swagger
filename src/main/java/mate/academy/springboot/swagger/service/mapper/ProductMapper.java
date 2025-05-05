package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.requests.ProductRequestsDto;
import mate.academy.springboot.swagger.model.dto.responses.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product mapToModel(ProductRequestsDto productRequestsDto) {
        Product product = new Product();
        product.setPrice(productRequestsDto.getPrice());
        product.setTitle(product.getTitle());
        return product;
    }

    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        return productResponseDto;
    }
}
