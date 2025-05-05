package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    List<Product> findAllProductWithParam(Integer count, Integer page, String sortBy);

    List<Product> findAllByPriseBetween(BigDecimal from, BigDecimal to,
                                        Integer count, Integer page, String sortBy);
}
