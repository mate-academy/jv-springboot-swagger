package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product saveOrUpdate(Product product);

    Product get(Long id);

    void delete(Long id);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> getAllBetweenTwoPrices(BigDecimal priceFrom,
                                         BigDecimal priceTo,
                                         PageRequest pageRequest);
}
