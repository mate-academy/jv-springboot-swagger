package mate.academy.springboot.swagger.repository.specification.product;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.specification.SpecificationManager;
import mate.academy.springboot.swagger.repository.specification.SpecificationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecificationManager implements SpecificationManager<Product> {

    private final Map<String, SpecificationProvider<Product>> providers;

    @Autowired
    public ProductSpecificationManager(List<SpecificationProvider<Product>> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(SpecificationProvider::getFilterKey,
                        Function.identity()));
    }

    @Override
    public Specification<Product> get(String key, String param) {
        if (!providers.containsKey(key)) {
            throw new IllegalArgumentException("Key " + key
                    + " is not supported for data filtering");
        }
        return providers.get(key).getSpecification(param);
    }
}
