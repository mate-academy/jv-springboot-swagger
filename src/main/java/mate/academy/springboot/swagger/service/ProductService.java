package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> getAllWherePaginationSort();

    List<Product> getAllWhereBetweenPriceSort(BigDecimal min, BigDecimal max);
}
