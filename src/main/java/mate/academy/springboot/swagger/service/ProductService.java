package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    void delete(Long id);

    List<Product> findAllByPriceBetween(PageRequest pageRequest, BigDecimal from, BigDecimal to);

    List<Product> findAll(PageRequest pageRequest);
}
