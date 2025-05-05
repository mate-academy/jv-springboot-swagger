package mate.academy.springboot.swagger.dto;

import mate.academy.springboot.swagger.model.Product;

public interface ProductMapper {
    Product toModel(ProductRequestDto productRequestDto);
}
