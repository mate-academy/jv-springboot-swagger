package mate.academy.springboot.swagger.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dtoObj);
}
