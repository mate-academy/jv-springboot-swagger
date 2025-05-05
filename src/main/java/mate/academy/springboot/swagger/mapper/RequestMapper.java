package mate.academy.springboot.swagger.mapper;

public interface RequestMapper<D, T> {
    T mapToModel(D dto);
}
