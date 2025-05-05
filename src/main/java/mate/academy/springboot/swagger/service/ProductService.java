package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product save(Product product);

    Product findById(Long id);

    void delete(Product product);

    Product update(Product product);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable);
}
