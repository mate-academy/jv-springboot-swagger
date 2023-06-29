package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<K, V, M> {
    M mapToModel(K dto);

    V mapToDto(M m);
}
