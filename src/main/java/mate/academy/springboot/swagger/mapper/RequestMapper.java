package mate.academy.springboot.swagger.mapper;

public interface RequestMapper<M, D> {
    M toModel(D dto);
}
