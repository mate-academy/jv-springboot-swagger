package mate.academy.springboot.swagger.dto;

public interface Mapper<M, T, V> {
    M toModel(T requestDto);

    V toResponseDto(M model);
}
