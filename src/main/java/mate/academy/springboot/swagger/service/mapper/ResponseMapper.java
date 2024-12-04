package mate.academy.springboot.swagger.service.mapper;

public interface ResponseMapper<M, S> {
    S mapToDto(M model);
}
