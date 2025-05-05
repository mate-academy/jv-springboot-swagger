package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    @Query("from Product p where p.price between ?1 and ?2")
    List<Product> findAll(BigDecimal priceFrom, BigDecimal priceTo, PageRequest pageRequest);
}
