package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findProductsByPriceBetween(@Param("from") BigDecimal from,
                                             @Param("to") BigDecimal to,
                                             Pageable pageable);
}
