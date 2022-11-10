package mate.academy.springboot.swagger.services;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getAllProductWherePriceBetween(BigDecimal from,
                                                 BigDecimal to, PageRequest pageRequest);
}
