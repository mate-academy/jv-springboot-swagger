package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Product update(Product product, Long id);

    Page<Product> findAll(PageRequest req);

    Page<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest req);
}
