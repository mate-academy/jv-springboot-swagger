package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<D, T> {
    D toDto(T t);
}
