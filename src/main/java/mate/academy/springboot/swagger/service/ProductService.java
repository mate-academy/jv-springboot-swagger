package mate.academy.springboot.swagger.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    List<Product> findAll(Map<String, String> params, Set<String> ignoreRequestParams,
                          PageRequest pageRequest);

    void delete(Long id);
}
