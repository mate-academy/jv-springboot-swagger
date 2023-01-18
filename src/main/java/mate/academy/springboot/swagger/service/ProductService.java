package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createOrUpdate(Product product);

    Product getById(Long id);

    void delete(Long id);

    List<Product> findAll(Pageable pagination);

    List<Product> findAllBetweenPrice(
            BigDecimal priceFrom, BigDecimal priceTo, Pageable pagination
    );
}
