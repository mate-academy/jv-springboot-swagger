package mate.academy.springboot.swagger.service.mapper;

public interface RequestDtoMapper<D, E> {
    E mapToEntity(D d);
}
