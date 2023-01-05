package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Product add(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    void update(Product product);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getAllBetweenPrice(BigDecimal from, BigDecimal to, PageRequest pageRequest);

    Sort getSorter(String orderBy);
}
