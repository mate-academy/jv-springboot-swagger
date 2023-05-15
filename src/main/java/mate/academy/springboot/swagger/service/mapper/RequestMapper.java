package mate.academy.springboot.swagger.service.mapper;

public interface RequestMapper<D, T> {
    T mapToModel(D dto);
}
