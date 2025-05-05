package mate.academy.springboot.swagger.service.specification.product;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductGreaterOrEqualsProvider implements SpecificationProvider<Product> {
    private static final String FILTER_KEY = "priceFrom";
    private static final String FILTER_FIELD = "price";

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return ((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(FILTER_FIELD), params[0]));
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
