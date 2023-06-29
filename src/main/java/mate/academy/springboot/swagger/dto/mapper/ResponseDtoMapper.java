package mate.academy.springboot.swagger.dto.mapper;

public interface ResponseDtoMapper<D, M> {
    D toDto(M model);
}
