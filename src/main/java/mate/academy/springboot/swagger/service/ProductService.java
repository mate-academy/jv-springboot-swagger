package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(Double from, Double to, PageRequest pageRequest);

    void deleteById(Long id);

    Product findById(Long id);

    Product save(Product product);
}
