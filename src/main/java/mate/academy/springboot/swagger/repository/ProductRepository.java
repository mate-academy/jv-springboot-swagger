package mate.academy.springboot.swagger.repository;

import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("from Product p where  p.price between ?1 and ?2")
    List<Product> findAllByPriceBetween(Double from, Double to, PageRequest pageRequest);

}

