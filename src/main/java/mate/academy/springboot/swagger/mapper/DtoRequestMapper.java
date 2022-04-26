package mate.academy.springboot.swagger.mapper;

public interface DtoRequestMapper<D, C> {
    C toEntity(D dto);
}
