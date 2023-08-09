package mate.academy.springboot.swagger.service;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    List<Product> getAll(PageRequest request);

    void delete(Long id);

    List<Product> getAllByPriceBetween(Long from, Long to, PageRequest pageRequest);
}
