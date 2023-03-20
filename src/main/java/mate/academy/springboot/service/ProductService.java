package mate.academy.springboot.service;

import mate.academy.springboot.model.Product;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
