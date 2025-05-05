package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    Product update(Product product);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getAllByPrice(BigDecimal from, BigDecimal to,
                                PageRequest pageRequest);

    void delete(Long id);
}
