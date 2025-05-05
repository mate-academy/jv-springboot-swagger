package mate.academy.springboot.swagger.dto.mapper;

public interface DtoMapper<M,R,Q> {
    M toModel(R requestDto);

    Q toDto(M model);
}
