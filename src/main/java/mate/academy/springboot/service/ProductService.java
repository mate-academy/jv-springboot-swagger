package mate.academy.springboot.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> findAllProducts(PageRequest pageRequest);

    List<Product> findAllByProductsPriceBetween(BigDecimal from, BigDecimal to,
                                                PageRequest pageRequest);
}
