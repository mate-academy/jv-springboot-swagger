package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<T, R, D> {
    T toModel(D dto);

    R toDto(T entity);
}
