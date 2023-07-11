package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {

    Product save(Product product);

    Product get(Long id);

    Product update(Product product);

    void delete(Long id);

    Page<Product> findAll(PageRequest pageRequest);

    Page<Product> findAllBetweenPrices(BigDecimal from, BigDecimal to,
                                       PageRequest pageRequest);

    PageRequest getPageRequest(Integer page, Integer count, String sortBy);
}
