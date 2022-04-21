package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> findAll();

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findByPriceBetween(BigDecimal from, BigDecimal to);
}
