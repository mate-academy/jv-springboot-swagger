package mate.academy.springboot.swagger.service.mapper;

public interface RequestMapperDto<D, T> {
    T mapToModel(D dto);
}
