package dto.mapper;

public interface DtoMapper<R, M, S> {
    M mapToModel(R requestDto);

    S mapToDto(M model);
}
