package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<T, D> {
    D toDto(T t);
}
