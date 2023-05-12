package mate.academy.springboot.swagger.service.mapper;

public interface RequestMapper<K, W> {
    K toModel(W requestDto);
}
