package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable);
}
