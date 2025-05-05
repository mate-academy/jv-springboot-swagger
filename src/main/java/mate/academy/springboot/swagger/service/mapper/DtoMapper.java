package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<D, R, M> {
    M mapToModel(D dto);

    R mapToDto(M model);
}
