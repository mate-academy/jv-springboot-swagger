package mate.academy.springboot.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
