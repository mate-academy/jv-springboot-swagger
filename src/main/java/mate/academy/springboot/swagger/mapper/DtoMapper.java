package mate.academy.springboot.swagger.mapper;

public interface DtoMapper<M, R, D> {
    M toModel(R requestDto);

    D toDto(M model);
}
