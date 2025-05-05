package mate.academy.springboot.swagger.mapper;

public interface ResponseMapper<T, K> {
    T toDto(K model);
}
