package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public interface ProductService {
    Product get(Long id);

    List<Product> findAll(PageRequest pageRequest);

    Product save(Product product);

    void delete(Long id);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);

    Sort sortData(String sortBy);
}
