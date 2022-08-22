package mate.academy.springboot.swagger.mapper;

public interface ResponseMapper<M, D> {
    D toDto(M model);
}
