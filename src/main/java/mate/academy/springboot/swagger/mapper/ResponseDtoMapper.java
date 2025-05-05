package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<D, C> {
    D toDto(C object);
}
