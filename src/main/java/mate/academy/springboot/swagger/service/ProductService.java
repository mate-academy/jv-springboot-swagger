package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    Product update(Product product);

    void delete(Long id);
}
