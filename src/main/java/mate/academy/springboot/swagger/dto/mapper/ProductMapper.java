package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductRequestDto, ProductResponseDto> {
    @Override
    public ProductResponseDto mapToDto(Product model) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(model.getId());
        responseDto.setTitle(model.getTitle());
        responseDto.setPrice(model.getPrice());
        return responseDto;
    }

    @Override
    public Product mapToModel(ProductRequestDto requestDtp) {
        Product product = new Product();
        product.setPrice(requestDtp.getPrice());
        product.setTitle(requestDtp.getTitle());
        return product;
    }
}
