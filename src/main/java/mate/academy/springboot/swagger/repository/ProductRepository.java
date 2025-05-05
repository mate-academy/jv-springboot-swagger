package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findAllByPriceBetweenAndPagination(BigDecimal from,
                                                     BigDecimal to,
                                                     Pageable pageable);
}
