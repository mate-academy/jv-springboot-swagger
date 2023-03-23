package mate.academy.springboot.swagger.mapper;

public interface RequestDtoMapper<E, D> {
    E toModel(D requestDto);
}
