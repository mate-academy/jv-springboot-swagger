package mate.academy.springboot.swagger.dto.mapper;

public interface RequestMapper <D, T> {
    T toModel(D dto);
}
