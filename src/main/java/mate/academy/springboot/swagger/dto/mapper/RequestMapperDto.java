package mate.academy.springboot.swagger.dto.mapper;

public interface RequestMapperDto<D, M> {
    M toModel(D dto);
}
