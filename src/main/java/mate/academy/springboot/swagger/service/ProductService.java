package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product save(Product product);

    Product getProductById(Long id);

    void delete(Long id);

    List<Product> getAll();
}
