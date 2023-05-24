package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<D, T, S> {
    D toModel(T requestDto);

    S toDto(D model);
}
