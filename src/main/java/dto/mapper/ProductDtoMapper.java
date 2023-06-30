package dto.mapper;

import dto.request.ProductRequestDto;
import dto.response.ProductResponseDto;
import model.Product;

public class ProductDtoMapper implements DtoMapper<ProductRequestDto, Product, ProductResponseDto> {
    @Override
    public Product mapToModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
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
