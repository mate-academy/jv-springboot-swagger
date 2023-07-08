package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {

    List<Product> findAll(Integer page, Integer count, String sortBy);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                        Integer page, Integer count,
                                        String sortBy);

    Product create(Product product);

    Product getById(Long id);

    Product update(Product product);

    void delete(Long id);
}
