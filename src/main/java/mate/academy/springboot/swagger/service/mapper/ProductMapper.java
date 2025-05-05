package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.request.RequestProductDto;
import mate.academy.springboot.swagger.dto.response.ResponseProductDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<Product, RequestProductDto, ResponseProductDto> {
    @Override
    public Product toModel(RequestProductDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return product;
    }

    @Override
    public ResponseProductDto toDto(Product product) {
        ResponseProductDto responseProductDto = new ResponseProductDto();
        responseProductDto.setId(product.getId());
        responseProductDto.setPrice(product.getPrice());
        responseProductDto.setTitle(product.getTitle());
        return responseProductDto;
    }
}
