package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void delete(Long id);

    Product update(Product product);

    List<Product> findAll(PageRequest pageRequest);

    public List<Product> findAllByPriceBetween(
            BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
