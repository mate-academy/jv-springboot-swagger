package mate.academy.springboot.swagger.dto.mapper;

public interface ResponseDtoMapper<D, T> {
    T toDto(D model);
}
