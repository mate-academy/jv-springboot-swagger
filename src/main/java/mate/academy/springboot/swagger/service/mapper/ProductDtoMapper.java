package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.dto.RequestDto;
import mate.academy.springboot.swagger.dto.ResponseDto;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper implements DtoMapper<RequestDto, ResponseDto, Product> {
    @Override
    public ResponseDto toDto(Product product) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setId(product.getId());
        responseDto.setTitle(product.getTitle());
        responseDto.setPrice(product.getPrice());
        return responseDto;
    }

    @Override
    public Product toModel(RequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        return product;
    }
}
