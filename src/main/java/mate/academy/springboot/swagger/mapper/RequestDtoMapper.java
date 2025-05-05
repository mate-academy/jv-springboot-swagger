package mate.academy.springboot.swagger.mapper;

public interface RequestDtoMapper<D, C> {
    C toModel(D object);
}
