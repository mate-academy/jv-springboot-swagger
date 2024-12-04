package mate.academy.springboot.swagger.service.mapper;

public interface RequestMapper<Q, M> {
    M mapToModel(Q requestDto);
}
