package mate.academy.springboot.swagger.service.mapper;

public interface ResponseMapperDto<D, T> {
    D mapToDto(T model);
}
