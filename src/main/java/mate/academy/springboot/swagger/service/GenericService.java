package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface GenericService<T> {
    T save(T entity);

    T find(Long id);

    List<T> findAll(PageRequest pageRequest);

    void update(T entity);

    void delete(Long id);
}
