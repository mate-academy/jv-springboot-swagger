package mate.academy.springboot.swagger.mapper.request;

public interface DtoRequestMapper<D, M> {
    M toModel(D dto);
}
