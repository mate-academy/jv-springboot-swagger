package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<M, D> {
    D toDto(M model);
}
