package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.ProductRequest;
import mate.academy.springboot.swagger.dto.ProductResponse;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements ResponseMapper<ProductResponse, Product>,
        RequestMapper<Product, ProductRequest> {

    @Override
    public ProductResponse toResponseDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setTitle(product.getTitle());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }

    @Override
    public Product toModel(ProductRequest requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return product;
    }
}
