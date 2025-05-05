package mate.academy.springboot.swagger.service.product;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    Product update(Product product);

    void delete(Long id);

    List<Product> findAll(int page, int count, String sortBy);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, int page,
                                        int count, String sortBy);
}
