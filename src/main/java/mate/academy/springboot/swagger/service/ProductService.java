package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product findById(Long id);

    List<Product> findAll();

    List<Product> findAllByPriceBetween(BigDecimal fromPrice, BigDecimal toPrice,
                                        PageRequest pageRequest);

    void deleteById(Long id);

    Product update(Product product);

}
