package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.domain.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(PageRequest pageRequest, BigDecimal from, BigDecimal to);
}
