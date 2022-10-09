package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T model);
}
