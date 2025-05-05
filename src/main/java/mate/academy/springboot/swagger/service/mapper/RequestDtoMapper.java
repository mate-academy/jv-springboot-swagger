package mate.academy.springboot.swagger.service.mapper;

public interface RequestDtoMapper<Q, M> {
    M mapToModel(Q q);
}
