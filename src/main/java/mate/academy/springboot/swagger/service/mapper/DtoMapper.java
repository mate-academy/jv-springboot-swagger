package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<D, T, M> {
    T toDto(M m);

    M toModel(D dto);
}
