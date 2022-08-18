package mate.academy.springboot.swagger.mapper;

public interface DtoRequestMapper<D, M> {
    M toModel(D dto);
}
