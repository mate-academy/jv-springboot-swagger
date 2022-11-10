package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
