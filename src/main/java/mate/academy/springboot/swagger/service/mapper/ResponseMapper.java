package mate.academy.springboot.swagger.service.mapper;

public interface ResponseMapper<D, T> {
    D mapToDto(T entity);
}
