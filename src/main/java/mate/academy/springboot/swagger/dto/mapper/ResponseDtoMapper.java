package mate.academy.springboot.swagger.dto.mapper;

public interface ResponseDtoMapper<T, D> {
    D mapToDto(T t);
}
