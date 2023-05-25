package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<M, R, V> {
    M toModel(R requestDto);

    V toDto(M model);
}
