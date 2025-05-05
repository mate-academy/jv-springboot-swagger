package mate.academy.springboot.mapper;

public interface RequestDtoMapper<D, M> {
    M toModel(D dto);
}
