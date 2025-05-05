package mate.academy.springboot.swagger.dto.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
