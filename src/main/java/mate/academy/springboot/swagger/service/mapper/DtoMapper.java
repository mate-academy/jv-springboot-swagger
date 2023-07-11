package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<P, D, T> {
    P mapToModel(T requestDto);

    D mapToDto(P model);
}
