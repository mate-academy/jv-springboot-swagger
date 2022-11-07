package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void delete(Long id);

    void update(Product product);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable);
}
