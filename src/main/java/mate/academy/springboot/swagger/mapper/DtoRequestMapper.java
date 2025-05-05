package mate.academy.springboot.swagger.mapper;

public interface DtoRequestMapper<D, T> {
    T toModel(D d);
}
