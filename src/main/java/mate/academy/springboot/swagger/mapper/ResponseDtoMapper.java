package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<M, D> {
    D toResponse(M model);
}
