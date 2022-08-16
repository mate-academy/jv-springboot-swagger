package mate.academy.springboot.swagger.repository.specification.product;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductTitleSpecification implements SpecificationProvider<Product> {
    private static final int FIRST_VALUE_INDEX = 0;
    private static final String FILTER_KEY = "title";
    private static final String FIELD_NAME = "title";

    @Override
    public Specification<Product> getSpecification(String[] titles) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(FIELD_NAME), titles[FIRST_VALUE_INDEX]);
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
