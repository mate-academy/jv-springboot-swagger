package mate.academy.springboot.swagger.model.dto.mapper;

public interface ResponseDtoMapper<D, M> {
    D mapToDto(M model);
}
