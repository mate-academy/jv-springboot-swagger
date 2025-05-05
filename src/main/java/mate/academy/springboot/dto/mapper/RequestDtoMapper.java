package mate.academy.springboot.dto.mapper;

public interface RequestDtoMapper<D, M> {
    M toModel(D d);
}
