package mate.academy.springboot.swagger.mapper.responce;

public interface ResponseDtoMapper<D, M> {
    D mapToDto(M model);
}
