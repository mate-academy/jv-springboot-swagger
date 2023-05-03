package mate.academy.springboot.swagger.repository.specification.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceToSpecificationProvider implements SpecificationProvider<Product> {

    private static final String FIELD_NAME = "price";
    private static final String FILTER_KEY = "priceTo";

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String param : params) {
                Predicate predicate = cb.lessThanOrEqualTo(root.get(FIELD_NAME),
                        BigDecimal.valueOf(Long.parseLong(param)));
                predicates.add(predicate);
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
