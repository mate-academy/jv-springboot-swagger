package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product save (Product product);

    Product getDyId(Long id);

    void deleteById(Long id);

    List<Product> getAll();

    List<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to);
}
