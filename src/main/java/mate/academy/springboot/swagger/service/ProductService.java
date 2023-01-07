package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product getById(Long id);
    void deleteById(Long id);
    Product update(Product product);
    List<Product> findAll(PageRequest pageRequest);
}
