package mate.academy.springboot.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
