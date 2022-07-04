package mate.academy.springboot.swagger.dao.specification.product;

import java.util.Arrays;
import javax.persistence.criteria.Predicate;
import mate.academy.springboot.swagger.dao.specification.SpecificationProvider;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceFromSpecification implements SpecificationProvider<Product> {
    private static final String FILTER_KEY = "from";
    private static final String FIELD_NAME = "price";
    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> {
            Long[] paramsLong = Arrays.stream(params).map(Long::valueOf).toArray(Long[]::new);
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(FIELD_NAME), paramsLong[0]);
            return criteriaBuilder.and(predicate);
        });
    }
}
