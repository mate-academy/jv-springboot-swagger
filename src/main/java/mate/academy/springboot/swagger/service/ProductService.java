package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    void delete(Long id);

    void update(Long id, Product product);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getProductsBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
