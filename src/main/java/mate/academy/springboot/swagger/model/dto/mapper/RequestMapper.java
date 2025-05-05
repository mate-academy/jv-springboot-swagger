package mate.academy.springboot.swagger.model.dto.mapper;

public interface RequestMapper<D, E> {
    E toEntity(D dto);
}
