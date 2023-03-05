package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<M, S> {
    S mapToDto(M m);
}
