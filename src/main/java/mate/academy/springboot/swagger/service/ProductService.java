package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;
import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    Product update(Product product);

    void deleteById(Long id);

    //Try to rename
    List<Product> findAllWithSortByPriceOrByTitle();

    List<Product> findAllByPriceBetween();
}
