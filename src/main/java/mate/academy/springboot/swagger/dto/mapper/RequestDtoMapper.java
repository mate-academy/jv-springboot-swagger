package mate.academy.springboot.swagger.dto.mapper;

public interface RequestDtoMapper<T, K> {
    T mapToModel(K k);
}
