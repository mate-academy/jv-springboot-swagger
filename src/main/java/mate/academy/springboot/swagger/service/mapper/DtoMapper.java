package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<R, T, M> {
    R mapToDto(M model);

    M mapToModel(T requestDto);
}
