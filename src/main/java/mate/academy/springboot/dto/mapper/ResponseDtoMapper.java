package mate.academy.springboot.dto.mapper;

public interface ResponseDtoMapper<D, M> {
    D toDto(M m);
}
