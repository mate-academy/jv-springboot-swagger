package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product save(Product product);

    Product findById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    List<Product> findAllPriceBetween(int from, int to, int count, int page, String sortBy);

    List<Product> findAll(int count, int page, String sortBy);
}
