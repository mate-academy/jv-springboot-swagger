package mate.academy.springboot.swagger.service;

import java.util.Map;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    void delete(Long id);

    Product update(Product product);

    Page<Product> getAll(Map<String, String> params);
}
