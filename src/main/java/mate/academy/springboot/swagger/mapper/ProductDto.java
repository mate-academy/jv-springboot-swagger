package mate.academy.springboot.swagger.mapper;

import mate.academy.springboot.swagger.dto.ProductRequest;
import mate.academy.springboot.swagger.dto.ProductResponse;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDto {
    public ProductResponse toDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setPrice(product.getPrice());
        productResponse.setTitle(product.getTitle());
        return productResponse;
    }

    public Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setPrice(productRequest.getPrice());
        product.setTitle(productRequest.getTitle());
        return product;
    }
}
