package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Page<Product> findAll(PageRequest pageRequest);

    List<Product> findAll(BigDecimal priceFrom, BigDecimal priceTo, PageRequest pageRequest);
}
