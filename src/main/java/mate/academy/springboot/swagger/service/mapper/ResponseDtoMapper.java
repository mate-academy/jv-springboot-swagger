package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<T, D> {
    T mapToDto(D responseDto);
}
