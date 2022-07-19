package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    void remove(Long id);

    Product update(Product product);
    
    List<Product> findAll(Pageable pageable);

    List<Product> findAll(BigDecimal from, BigDecimal to, Pageable pageable);
}
