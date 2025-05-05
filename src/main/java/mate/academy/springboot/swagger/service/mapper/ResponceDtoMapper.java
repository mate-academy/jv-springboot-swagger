package mate.academy.springboot.swagger.service.mapper;

public interface ResponceDtoMapper<D, T> {
    D mapToDto(T model);
}
