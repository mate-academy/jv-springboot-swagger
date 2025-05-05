package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<RqT, E, Rst> {
    Rst toDto(E entity);

    E toEntity(RqT request);
}
