package mate.academy.springboot.swagger.service.mapper;

public interface RequestDtoMapper<T, D> {
    D mapToModel(T requestDto);
}
