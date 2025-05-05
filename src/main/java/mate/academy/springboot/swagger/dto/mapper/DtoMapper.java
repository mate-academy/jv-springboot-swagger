package mate.academy.springboot.swagger.dto.mapper;

public interface DtoMapper<M, Q, S> {
    M mapToModel(Q requestDto);

    S mapToDto(M model);
}
