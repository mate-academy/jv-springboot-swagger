package mate.academy.springboot.swagger.mapper;

public interface DtoMapper<D, S, E> {
    D toModel(S dto);

    E toDto(D model);
}
