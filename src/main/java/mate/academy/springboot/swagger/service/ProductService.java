package mate.academy.springboot.swagger.service;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    Page<Product> getAllWithSort(PageRequest pageRequest);

    Page<Product> getAllWherePriceBetweenWithSort(Double from, Double to, PageRequest pageRequest);
}
