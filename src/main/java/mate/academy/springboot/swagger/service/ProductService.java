package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    Product update(Product product);

    List<Product> getAllProducts(PageRequest pageRequest);

    List<Product> getAllByPriceBetween(Pageable pageRequest, BigDecimal from, BigDecimal to);

    void deleteById(Long id);
}
