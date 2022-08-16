package mate.academy.springboot.swagger.repository.specification.product;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceToSpecification implements SpecificationProvider<Product> {
    private static final int FIRST_VALUE_INDEX = 0;
    private static final String FILTER_KEY = "priceTo";
    private static final String FIELD_NAME = "price";

    @Override
    public Specification<Product> getSpecification(String[] prices) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(
                root.get(FIELD_NAME),
                BigDecimal.valueOf(Double.parseDouble(prices[FIRST_VALUE_INDEX])));
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
