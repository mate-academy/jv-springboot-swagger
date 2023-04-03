package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<D, T, V> {
    D mapToModel(T dto);

    V mapToDto(D t);
}