package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    List<Product> findAllProductWithParam(PageRequest pageRequest);

    List<Product> findAllByPriseBetween(BigDecimal from, BigDecimal to);
}
