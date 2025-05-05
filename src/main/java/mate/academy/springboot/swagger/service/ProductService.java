package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    Page<Product> getAll(Pageable pageRequest);

    Page<Product> getByPriceBetween(BigDecimal min, BigDecimal max,
                                    Pageable pageRequest);
}
