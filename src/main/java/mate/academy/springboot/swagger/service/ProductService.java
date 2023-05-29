package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    void delete(Long id);

    Product update(Long id, Product product);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPrice(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
