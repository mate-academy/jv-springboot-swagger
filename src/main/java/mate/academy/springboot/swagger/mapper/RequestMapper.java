package mate.academy.springboot.swagger.mapper;

public interface RequestMapper<T, I> {
    T toModel(I i);
}
