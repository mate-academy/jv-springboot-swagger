package mate.academy.springboot.swagger.service.mapper;

public interface RequestDtoMapper<M, Q> {
    M mapToModel(Q dto);
}
