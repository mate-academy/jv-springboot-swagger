package mate.academy.springboot.swagger.service;

import java.util.List;

public interface GenericService<T> {
    T save(T entity);

    T find(Long id);

    List<T> findAll();

    void update(T entity);

    void delete(Long id);
}
