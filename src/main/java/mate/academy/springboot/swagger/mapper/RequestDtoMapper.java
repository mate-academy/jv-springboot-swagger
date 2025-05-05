package mate.academy.springboot.swagger.mapper;

public interface RequestDtoMapper<Q, M> {
    M mapToModel(Q dto);
}
