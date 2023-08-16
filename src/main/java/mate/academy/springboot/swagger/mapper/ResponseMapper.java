package mate.academy.springboot.swagger.mapper;

public interface ResponseMapper<T, D> {
    D mapToDto(T model);
}
