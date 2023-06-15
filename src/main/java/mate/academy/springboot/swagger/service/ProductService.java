package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    Product update(Product product);

    void deleteById(Long id);

    List<Product> getAllProducts();

    List<Product> getAllProducts(PageRequest pageRequest);

    List<Product> getAllProductsByPriceBetween(BigDecimal from, BigDecimal to,
                                               PageRequest pageRequest);
}
