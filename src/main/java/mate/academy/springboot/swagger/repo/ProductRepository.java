package mate.academy.springboot.swagger.repo;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable);
}
