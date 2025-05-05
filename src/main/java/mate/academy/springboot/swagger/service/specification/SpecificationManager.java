package mate.academy.springboot.swagger.service.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationManager<T> {
    Specification<T> getSpecification(String filterKey, String[] params);
}
