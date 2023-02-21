package mate.academy.springboot.swagger.mapper;

public interface DtoMapper<R, A, M> {
    M fromDto(R dto);

    A toDto(M model);
}
