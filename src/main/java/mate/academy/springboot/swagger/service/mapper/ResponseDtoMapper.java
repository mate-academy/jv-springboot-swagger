package mate.academy.springboot.swagger.service.mapper;

public interface ResponseDtoMapper<D, M> {
    D mapToDto(M model);
}
