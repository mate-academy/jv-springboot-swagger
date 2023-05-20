package mate.academy.springboot.swagger.mapper;

public interface DtoMapper<R, S, M> {
    M mapToModel(R requestDto);

    S mapToDto(M model);
}
