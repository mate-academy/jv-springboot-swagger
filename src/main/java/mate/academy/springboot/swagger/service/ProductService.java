package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product add(Product product);

    Product get(Long id);

    List<Product> getAll(Pageable pageable);

    List<Product> getAllPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable);

    void update(Product product);

    void delete(Long id);
}
