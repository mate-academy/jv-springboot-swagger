package mate.academy.springboot.swagger.mapper;

public interface DtoResponseMapper<D, C> {
    D toDto(C object);
}
