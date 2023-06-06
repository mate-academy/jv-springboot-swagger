package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product add(Product product);

    Product get(Long id);

    Product update(Product product);

    void delete(Long id);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPricing(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
