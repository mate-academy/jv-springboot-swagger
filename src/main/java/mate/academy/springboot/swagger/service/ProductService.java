package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product add(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Product update(Product product, Long id);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
