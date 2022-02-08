package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    void delete(Long id);

    void update(Product product);

    void injection();

    List<Product> findAll(Integer page, Integer size, String sortBy);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                        Integer page, Integer size, String sortBy);
}
