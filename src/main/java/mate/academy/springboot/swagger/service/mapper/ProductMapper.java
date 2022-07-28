package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponceDto;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements RequestDtoMapper<ProductRequestDto, Product>,
        ResponceDtoMapper<ProductResponceDto, Product> {
    private final ProductService productService;

    public ProductMapper(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }

    @Override
    public ProductResponceDto mapToDto(Product model) {
        ProductResponceDto productResponceDto = new ProductResponceDto();
        productResponceDto.setId(model.getId());
        productResponceDto.setTitle(model.getTitle());
        productResponceDto.setPrice(model.getPrice());
        return productResponceDto;
    }
}
