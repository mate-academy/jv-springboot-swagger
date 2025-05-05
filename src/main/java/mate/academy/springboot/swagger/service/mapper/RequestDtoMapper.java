package mate.academy.springboot.swagger.service.mapper;

public interface RequestDtoMapper<D, M> {
    M toModel(D requestDto);
}
