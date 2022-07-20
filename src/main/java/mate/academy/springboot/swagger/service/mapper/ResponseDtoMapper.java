package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<E, D> {
    D mapToDto(E e);
}
