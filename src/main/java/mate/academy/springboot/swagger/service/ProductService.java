package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService extends AbstractService<Product> {
    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);

}
