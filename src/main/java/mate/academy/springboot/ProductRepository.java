package mate.academy.springboot;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByProductsPriceBetween(BigDecimal from, BigDecimal to,
                                                PageRequest pageRequest);
}
