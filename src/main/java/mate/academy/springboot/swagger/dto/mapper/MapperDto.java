package mate.academy.springboot.swagger.dto.mapper;

import mate.academy.springboot.swagger.model.Product;

public interface MapperDto<Q, S, M> {
    S toDto(M model);

    Product toModel(Q dtoRequest);
}
