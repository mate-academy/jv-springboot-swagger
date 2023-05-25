package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<E, D> {
    D toDto(E entity);
}
