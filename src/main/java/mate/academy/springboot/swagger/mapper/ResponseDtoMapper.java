package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<S, M> {
    S mapToDto(M t);
}
