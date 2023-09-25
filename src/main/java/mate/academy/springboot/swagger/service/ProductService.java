package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;

public interface ProductService extends CrudGenericService<Product> {
    @Override
    Product add(Product product);

    @Override
    Product update(Product product);

    List<Product> getAll(PageRequest pageRequest);

    List<Product> getProductsByPriceBetween(
            PageRequest pageRequest,
            BigDecimal from,
            BigDecimal to);
}
