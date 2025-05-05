package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<E, Q, S> {
    E toModel(Q requestDto);

    S toDto(E element);
}
