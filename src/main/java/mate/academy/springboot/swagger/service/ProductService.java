package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void delete(Long id);

    Product update(Product product);

    List<Product> findAll();
}
