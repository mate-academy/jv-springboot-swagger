package mate.academy.springboot.swagger.mapper;

public interface RequestDtoMapper<D, E> {
    E mapToEntity(D d);
}
