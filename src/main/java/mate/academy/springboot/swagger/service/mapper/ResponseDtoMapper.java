package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<S, M> {
    S mapToDto(M m);
}
