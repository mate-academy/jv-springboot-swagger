package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.exception.DataProcessException;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product create(Product product);

    Product find(Long id) throws DataProcessException;

    void delete(Long id);

    Product update(Product toModel);

    List<Product> getAll(Map<String, String> params, Pageable pageable);
}
