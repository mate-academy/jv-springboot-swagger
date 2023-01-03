package mate.academy.springboot.swagger.mapper;

public interface ResponseMapper<T, I> {
    I toDto(T t);
}
