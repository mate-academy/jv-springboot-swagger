package mate.academy.springboot.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortOrderUtil {
    public Sort getSorted(String sortBy) {
        Sort.Order order;
        if (sortBy.contains(":")) {
            String[] fieldAndOrder = sortBy.split(":");
            order = new Sort.Order(
                    Sort.Direction.valueOf(fieldAndOrder[1]),
                    fieldAndOrder[0]);
        } else {
            order = Sort.Order.asc(sortBy);
        }
        return Sort.by(order);
    }
}
