package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    void delete(Long id);

    Product update(Long id, Product product);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
