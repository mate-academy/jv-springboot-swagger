package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> getAllByPrice(BigDecimal from, BigDecimal to, PageRequest pageRequest);

    List<Product> getAll(PageRequest pageRequest);
}
