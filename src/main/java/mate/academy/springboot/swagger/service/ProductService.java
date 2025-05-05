package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {

    Product create(Product product);

    Product update(Product product);

    void delete(Long id);

    Product getById(Long id);

    Page<Product> getAll(PageRequest pageRequest);

    Page<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
