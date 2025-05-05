package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface AbstractService<T> {
    T save(T t);

    T get(Long id);

    List<T> getAll(PageRequest pageRequest);

    void delete(Long id);

    T update(T t);
}
