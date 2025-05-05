package service;

import java.math.BigDecimal;
import java.util.List;
import model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product get(Long id);

    Product update(Product product);

    void delete(Long id);

    Product save(Product product);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(BigDecimal from,
                                        BigDecimal to,
                                        Pageable pageable);
}
