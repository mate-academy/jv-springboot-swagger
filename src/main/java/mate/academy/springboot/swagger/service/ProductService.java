package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product add(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    void update(Product product);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getAllBetweenPrice(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
