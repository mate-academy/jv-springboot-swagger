package mate.academy.springboot.swagger.mapper;

public interface DtoRequestMapper<D, M> {
    M fromDto(D dto);
}
