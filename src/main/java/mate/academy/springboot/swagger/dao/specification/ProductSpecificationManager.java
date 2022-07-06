package mate.academy.springboot.swagger.dao.specification;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecificationManager implements SpecificationManager<Product> {
    private final Map<String, SpecificationProvider<Product>> providersMap;

    public ProductSpecificationManager(List<SpecificationProvider<Product>> productSpecification) {
        this.providersMap = productSpecification.stream()
                .collect(Collectors.toMap(SpecificationProvider<Product>::getFilterKey,
                        Function.identity()));
    }

    @Override
    public Specification<Product> get(String filterKey, String[] params) {
        if (!providersMap.containsKey(filterKey)) {
            throw new RuntimeException("Key " + filterKey
                    + " is not supported for data filter  ing");
        }
        return providersMap.get(filterKey).getSpecification(params);
    }
}
