package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Product SET title = :#{#product.title}, price = :#{#product.price} "
            + "WHERE id = :#{#product.id}")
    int update(Product product);

    List<Product> getAllByPriceBetween(BigDecimal priceFrom, BigDecimal priceTo, Pageable pageable);
}
