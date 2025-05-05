package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product get(Long id);

    Product add(Product product);

    void delete(Long id);

    Product update(Long id, Product product);

    List<Product> getAllByPriceBetween(Long from, Long to, PageRequest pageRequest);

    List<Product> getAll(PageRequest pageRequest);
}
