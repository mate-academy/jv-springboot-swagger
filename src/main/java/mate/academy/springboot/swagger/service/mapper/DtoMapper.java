package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<P, D, T> {
    P mapToModel(T t);

    D mapToDto(P dto);
}