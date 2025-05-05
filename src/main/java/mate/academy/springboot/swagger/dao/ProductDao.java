package mate.academy.springboot.swagger.dao;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(BigDecimal loweBound,
                                        BigDecimal upperBound,
                                        Pageable pageable);
}
