package mate.academy.springboot.swagger.dto;

public interface RequestDtoMapper<D, T> {
    T toModel(D d);
}
