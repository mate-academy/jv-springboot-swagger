package mate.academy.springboot.swagger.model.dto.mapper;

public interface RequestDtoMapper<D, M> {
    M mapToModel(D dto, Long id);
}
