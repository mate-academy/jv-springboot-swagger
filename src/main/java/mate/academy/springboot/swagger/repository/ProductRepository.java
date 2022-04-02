package mate.academy.springboot.swagger.repository;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query(value = "from Product p where p.price between ?1 and ?2")
    Page<Product> findAllUsersWithPagination(BigDecimal from, BigDecimal to, Pageable pageable);
}
