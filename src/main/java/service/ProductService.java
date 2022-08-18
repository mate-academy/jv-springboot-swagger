package service;

import java.math.BigDecimal;
import java.util.List;
import model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    void deleteById(Long id);

    Product getById(Long id);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);

    List<Product> findAll(PageRequest pageRequest);
}
