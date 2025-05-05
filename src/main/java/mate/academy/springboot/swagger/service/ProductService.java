package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product findById(Long id);

    void delete(Long id);

    Product update(Product product);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> getAllByPriceBetween(Long from, Long to, PageRequest pageRequest);
}
