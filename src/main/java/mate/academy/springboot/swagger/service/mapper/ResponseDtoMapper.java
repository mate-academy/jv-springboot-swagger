package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<D,T> {
    D toDto(T model);
}
