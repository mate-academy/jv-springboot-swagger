package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product save(Product product);

    Product findById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    List<Product> findAll(Integer count, Integer page, String sortBy);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                        Integer page, Integer count, String sortBy);
}
