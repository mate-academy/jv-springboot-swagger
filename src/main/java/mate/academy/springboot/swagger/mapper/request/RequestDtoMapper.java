package mate.academy.springboot.swagger.mapper.request;

public interface RequestDtoMapper<D, M> {
    M mapToModel(D dto);
}
