package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product add(Product product);

    Product getById(Long id);

    void delete(Long id);

    Product update(Product product);

    List<Product> getAll(Pageable pageable);

    List<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable);
}
