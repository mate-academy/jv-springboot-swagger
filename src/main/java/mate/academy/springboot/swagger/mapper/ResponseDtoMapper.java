package mate.academy.springboot.swagger.mapper;

public interface ResponseDtoMapper<D,M> {
    D toDto(M entity);

    M toModel(D dto);
}
