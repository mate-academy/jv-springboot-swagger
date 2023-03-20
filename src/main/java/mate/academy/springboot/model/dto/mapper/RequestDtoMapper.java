package mate.academy.springboot.model.dto.mapper;

public interface RequestDtoMapper<D, M> {
    M toModel(D dto);
}
