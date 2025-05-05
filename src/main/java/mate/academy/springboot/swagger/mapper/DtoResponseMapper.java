package mate.academy.springboot.swagger.mapper;

public interface DtoResponseMapper<M, D> {
    D toDto(M model);
}
