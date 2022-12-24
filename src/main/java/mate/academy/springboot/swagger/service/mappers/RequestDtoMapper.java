package mate.academy.springboot.swagger.service.mappers;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
