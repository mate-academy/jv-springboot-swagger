package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Optional<Product> get(Long id);

    void delete(Long id);

    Product update(Product product);

    List<Product> getAllProducts(PageRequest request);

    List<Product> getAllProductsByPrice(BigDecimal from, BigDecimal to, PageRequest request);
}
