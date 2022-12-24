package mate.academy.springboot.swagger.service.mappers;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
