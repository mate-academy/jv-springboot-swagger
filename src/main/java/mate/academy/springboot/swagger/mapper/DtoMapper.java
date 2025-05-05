package mate.academy.springboot.swagger.mapper;

public interface DtoMapper<D, R, T> extends RequestMapper<D, T>, ResponseMapper<T, R> {
}
