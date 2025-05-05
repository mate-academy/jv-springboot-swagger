package mate.academy.springboot.swagger.service.mapper;

/**
 * @param <M> - model of our entity
 * @param <R> - request dto of our entity
 * @param <D> - response dto of our entity
 */

public interface Mapper<M, R, D> {
    M toModel(R r);

    D toDto(M m);
}
