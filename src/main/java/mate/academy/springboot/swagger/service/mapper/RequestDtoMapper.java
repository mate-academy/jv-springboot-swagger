package mate.academy.springboot.swagger.service.mapper;

import mate.academy.springboot.swagger.model.Product;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
