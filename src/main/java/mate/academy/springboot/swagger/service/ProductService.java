package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void update(Product product);

    void deleteById(Long id);

    List<Product> findAll();

    List<Product> findAll(Integer page, Integer size, String sortBy);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                        Integer page, Integer size, String sortBy);
}
