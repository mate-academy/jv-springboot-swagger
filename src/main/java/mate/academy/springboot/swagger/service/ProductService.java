package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product add(Product product);

    Product getById(Long id);

    void delete(Long id);

    Product update(Product product);

    List<Product> findAll();

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(Double from, Double to,
                                          PageRequest pageRequest);
}
