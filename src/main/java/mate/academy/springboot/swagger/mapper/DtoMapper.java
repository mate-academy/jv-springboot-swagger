package mate.academy.springboot.swagger.mapper;

public interface DtoMapper<Q, S, M> extends RequestDtoMapper<Q, M>, ResponseDtoMapper<S, M> {
}
