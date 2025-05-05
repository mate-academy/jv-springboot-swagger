package mate.academy.springboot.swagger.model.repository;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to);
}
