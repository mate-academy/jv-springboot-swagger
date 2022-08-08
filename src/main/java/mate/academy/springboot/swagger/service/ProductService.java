package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product save(Product product);
    Product findById(Long id);
    void deleteById(Long id);
    void update(Product product);

    List<Product> getProductsByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);

    List<Product> findAll(PageRequest pageRequest);

}
