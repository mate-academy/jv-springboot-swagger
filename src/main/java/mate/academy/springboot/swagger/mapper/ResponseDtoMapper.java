package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<T, D> {
    D mapToDto(T model);
}
