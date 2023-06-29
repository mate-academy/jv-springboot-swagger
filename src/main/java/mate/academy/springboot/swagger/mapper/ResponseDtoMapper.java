package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<D, M> {
    D mapToDto(M model);
}
