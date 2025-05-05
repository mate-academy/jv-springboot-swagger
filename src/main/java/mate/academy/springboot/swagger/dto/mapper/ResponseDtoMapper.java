package mate.academy.springboot.swagger.dto.mapper;

public interface ResponseDtoMapper<K, T> {
    T mapToDto(K k);
}
