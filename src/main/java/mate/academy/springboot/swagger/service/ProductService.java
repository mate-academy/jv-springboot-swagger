package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void delete(Long id);

    Page<Product> getAll(PageRequest request);

    List<Product> getAllByPrice(BigDecimal from, BigDecimal to, PageRequest request);
}
