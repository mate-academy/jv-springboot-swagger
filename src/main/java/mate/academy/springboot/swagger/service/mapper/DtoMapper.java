package mate.academy.springboot.swagger.service.mapper;

public interface DtoMapper<R, M, S> {
    M toModel(R rq);

    S toDto(M m);
}
