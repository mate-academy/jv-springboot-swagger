package mate.academy.springboot.swagger.repository;

import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductById(Long id);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to);
}
