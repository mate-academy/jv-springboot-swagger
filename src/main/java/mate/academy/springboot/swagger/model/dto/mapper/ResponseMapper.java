package mate.academy.springboot.swagger.model.dto.mapper;

public interface ResponseMapper<D, E> {
    D toDto(E entity);
}
