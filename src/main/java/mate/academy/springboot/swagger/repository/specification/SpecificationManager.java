package mate.academy.springboot.swagger.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationManager<T> {
    Specification<T> get(String key, String param);
}
