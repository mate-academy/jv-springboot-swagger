package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<D, M, T> {
    M toDto(T model);

    T toModel(D requestDto);
}
