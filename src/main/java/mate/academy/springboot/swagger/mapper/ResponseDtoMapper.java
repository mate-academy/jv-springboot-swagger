package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<E, D> {
    D mapToDto(E e);
}
