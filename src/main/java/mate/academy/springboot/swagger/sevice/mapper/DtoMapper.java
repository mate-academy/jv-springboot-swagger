package mate.academy.springboot.swagger.sevice.mapper;

public interface DtoMapper<E, S, Q> {
    S toDto(E entity);

    E toEntity(Q request);
}
