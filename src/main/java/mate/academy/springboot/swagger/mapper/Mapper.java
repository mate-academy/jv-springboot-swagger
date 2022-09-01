package mate.academy.springboot.swagger.mapper;

public interface Mapper<S, V, U> {
    V toDto(U model);

    U toModel(S requestDto);
}

