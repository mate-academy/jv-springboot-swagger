package mate.academy.springboot.swagger.dto;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
