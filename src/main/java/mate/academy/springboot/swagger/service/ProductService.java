package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    Boolean deleteById(Long id);

    Product update(Product product);

    List<Product> findAll(PageRequest pageRequest);

    List<Product> findAllByPriceBetween(PageRequest pageRequest, BigDecimal from, BigDecimal to);
    // toDo: get all products where price is between two values received as a RequestParam inputs.
    //  Add pagination and ability to sort by price or by title in ASC or DESC order.
}
