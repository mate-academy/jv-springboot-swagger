package mate.academy.springboot.swagger.mapper;

public interface DtoResponseMapper<D, T> {
    D toDto(T t);
}
