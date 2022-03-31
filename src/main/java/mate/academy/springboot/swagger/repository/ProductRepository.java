package mate.academy.springboot.swagger.repository;

import static mate.academy.springboot.swagger.util.Queries.GET_MAX_PRICE_QUERY;
import static mate.academy.springboot.swagger.util.Queries.GET_MIN_PRICE_QUERY;
import static mate.academy.springboot.swagger.util.Queries.PRODUCT_QUERY_COUNT;
import static mate.academy.springboot.swagger.util.Queries.PRODUCT_QUERY_VALUE;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = PRODUCT_QUERY_VALUE, countQuery = PRODUCT_QUERY_COUNT, nativeQuery = true)
    Page<Product> findByPrice(BigDecimal from, BigDecimal to, Pageable pageable);

    @Query(value = GET_MAX_PRICE_QUERY, nativeQuery = true)
    BigDecimal getMaxPrice();

    @Query(value = GET_MIN_PRICE_QUERY, nativeQuery = true)
    BigDecimal getMinPrice();
}
