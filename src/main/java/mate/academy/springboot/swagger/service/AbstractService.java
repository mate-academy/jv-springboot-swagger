package mate.academy.springboot.swagger.service;

public interface AbstractService<T> {
    T save(T product);

    T get(Long id);

    void delete(Long id);

    T update(T product);
}
