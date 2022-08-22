package mate.academy.springboot.swagger.mapper.response;

public interface DtoResponseMapper<M, D> {
    D toDto(M model);
}
