package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product create(Product product);

    Product get(Long productId);

    void delete(Long productId);

    Product update(Product product, Long id);

    List<Product> findAll(Integer count, Integer page, String sortBy);

    List<Product> findAllBetweenPrice(Integer count, Integer page, String sortBy,
                                             BigDecimal from, BigDecimal to);
}
