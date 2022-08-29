package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product p set p.title = ?1, p.price = ?2 where p.id = ?3")
    void update(String title, BigDecimal price, Long id);

    @Query("from Product p where p.price between ?1 and ?2")
    List<Product> getProductByPriceBetween(BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
