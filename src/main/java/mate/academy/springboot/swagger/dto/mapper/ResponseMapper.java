package mate.academy.springboot.swagger.dto.mapper;

public interface ResponseMapper<D, T> {
    D toDto(T model);
}
