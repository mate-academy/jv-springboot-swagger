package mate.academy.springboot.swagger.dto.mapper;

public interface DtoMapper<R, S, M> {
    M toModel(R requestDto);

    S toDto(M model);
}
