package mate.academy.springboot.swagger.service;

public interface CrudGenericService<E> {
    E add(E model);

    E get(Long id);

    E update(E model);

    void delete(Long id);
}
