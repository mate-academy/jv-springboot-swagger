package mate.academy.springboot.swagger.repository.specification.product;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceFromSpecificationProvider implements SpecificationProvider<Product> {

    private static final String FIELD_NAME = "price";
    private static final String FILTER_KEY = "priceFrom";

    @Override
    public Specification<Product> getSpecification(String param) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(FIELD_NAME),
                BigDecimal.valueOf(Long.parseLong(param)));
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
