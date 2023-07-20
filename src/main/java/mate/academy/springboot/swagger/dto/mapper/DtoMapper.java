package mate.academy.springboot.swagger.dto.mapper;

public interface DtoMapper<R, M, D> {
    M mapToModel(R requestDto);

    D mapToDto(M model);
}
