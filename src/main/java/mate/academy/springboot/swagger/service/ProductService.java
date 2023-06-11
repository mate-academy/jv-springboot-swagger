package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService extends GenericService<Product> {
    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                        Pageable pageable);
}
