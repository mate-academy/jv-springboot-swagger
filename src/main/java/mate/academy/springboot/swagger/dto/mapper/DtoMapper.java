package mate.academy.springboot.swagger.dto.mapper;

public interface DtoMapper<T, Q, S> {
    public S toDto(T model);

    public T toModel(Q requestDto);
}
