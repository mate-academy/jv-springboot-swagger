package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;

public interface ProductService {

    public Product create(Product product);

    public Product get(Long productId);

    public void delete(Long productId);

    public Product update(Product product, Long productId);

    public List<Product> findAll(Integer count, Integer page, String sortBy);

    public List<Product> findAllBetweenPrice(Integer count, Integer page, String sortBy,
                                             BigDecimal from, BigDecimal to);
}
