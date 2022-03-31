package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM products AS p WHERE p.price BETWEEN ?1 AND ?2",
            countQuery = "SELECT count(*) FROM products AS p WHERE p.price BETWEEN ?1 AND ?2",
            nativeQuery = true)
    Page<Product> findByPrice(BigDecimal from, BigDecimal to, Pageable pageable);

    @Query(value = "SELECT max(products.price) FROM products", nativeQuery = true)
    BigDecimal getMaxPrice();

    @Query(value = "SELECT min(products.price) FROM products", nativeQuery = true)
    BigDecimal getMinPrice();
}
