package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    void delete(Long id);

    int update(Product product);

    List<Product> getAll(Integer page, Integer count, String sort);

    List<Product> getAllByPriceBetween(BigDecimal priceFrom, BigDecimal priceTo, Integer page,
                                       Integer count, String sort);
}
