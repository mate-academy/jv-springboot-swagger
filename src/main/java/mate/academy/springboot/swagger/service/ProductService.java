package mate.academy.springboot.swagger.service;

import java.util.List;
import java.util.Map;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(Product product);

    Product findById(Long id);

    void delete(Long id);

    Product update(Product toModel);

    List<Product> findAllByPrice(Map<String, String> params, Pageable pageable);

    List<Product> findAll(PageRequest pageRequest);
}
