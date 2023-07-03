package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(BigDecimal price, BigDecimal price2, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.title = ?1, p.price = ?2 WHERE p.id = ?3")
    void update(String title, BigDecimal price, Long id);
}
