package mate.academy.springboot.swagger.dto.mapper;

public interface RequestDtoMapper<D, M> {
    M toModel(D d);
}
