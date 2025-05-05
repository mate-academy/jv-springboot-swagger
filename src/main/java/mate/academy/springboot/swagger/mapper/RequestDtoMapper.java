package mate.academy.springboot.swagger.mapper;

public interface RequestDtoMapper<D, M> {
    M mapToModel(D dto);
}
