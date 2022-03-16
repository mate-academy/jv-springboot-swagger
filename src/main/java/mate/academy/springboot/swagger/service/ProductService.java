package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    void update(Product product);

    List<Product> findAll(Integer page, Integer count, String sortBy);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                        Integer page, Integer count, String sortBy);

}
