package mate.academy.springboot.swagger.mapper;

public interface RequestMapper<T, K> {
    T toModel(K dto);
}
