package mate.academy.springboot.swagger.mapper;

public interface DtoMapper<E, I, O> {
    O toDto(E entity);

    E toModel(I requestDto);
}
