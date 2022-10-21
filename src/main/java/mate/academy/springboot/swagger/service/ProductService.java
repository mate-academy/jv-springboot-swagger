package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    void delete(Long id);
}
