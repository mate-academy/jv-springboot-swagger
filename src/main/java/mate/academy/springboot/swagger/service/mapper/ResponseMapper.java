package mate.academy.springboot.swagger.service.mapper;

public interface ResponseMapper<K,W> {
    K toResponseDto(W model);
}
