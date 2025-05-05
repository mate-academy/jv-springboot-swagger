package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    void update(Product product);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(int from, int to, PageRequest pageRequest);
}
