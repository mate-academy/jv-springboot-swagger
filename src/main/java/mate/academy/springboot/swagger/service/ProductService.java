package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService extends AbstractService<Product> {
    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
