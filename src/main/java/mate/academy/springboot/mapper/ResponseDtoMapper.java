package mate.academy.springboot.mapper;

public interface ResponseDtoMapper<D, M> {
    D toDto(M model);
}
