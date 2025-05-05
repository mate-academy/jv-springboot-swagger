package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    List<Product> findAllByPriceBetween(BigDecimal fromPrice, BigDecimal toPrice,
                                        PageRequest pageRequest);

    List<Product> findAll(PageRequest pageRequest);
}
