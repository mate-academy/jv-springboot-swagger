package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;

public class ProductMapper implements RequestMapper<ProductRequestDto, Product>,
        ResponseMapper<ProductResponseDto, Product> {
    @Override
    public Product toModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto toDto(Product model) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setPrice(model.getPrice());
        return dto;
    }
}
