package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
        JpaRepository<Product, Long> {
    Page<Product> getProductsByPriceBetween(BigDecimal from,
                                            BigDecimal to,
                                            PageRequest pageRequest);
}
