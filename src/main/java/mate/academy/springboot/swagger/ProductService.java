package mate.academy.springboot.swagger;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);

    List<Product> getAllProducts(PageRequest pageRequest);
}
