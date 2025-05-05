package mate.academy.springboot.swagger.dto.mapper;

public interface ResponseMapperDto<D, M> {
    D toDto(M model);
}
