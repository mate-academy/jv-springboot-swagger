package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(PageRequest pageRequest, BigDecimal from, BigDecimal to);

    Product findById(Long id);

    void delete(Long id);

    Product update(Product product);

}
