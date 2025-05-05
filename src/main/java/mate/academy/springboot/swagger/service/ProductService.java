package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    Product update(Product product);

    void delete(Long id);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
