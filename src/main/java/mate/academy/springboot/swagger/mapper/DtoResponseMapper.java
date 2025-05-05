package mate.academy.springboot.swagger.mapper;

public interface DtoResponseMapper<D, M> {
    D toDto(M model);
}
