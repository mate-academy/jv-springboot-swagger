package mate.academy.springboot.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortOrderUtil {
    private static final String REGEX = ":";
    private static final int ORDER_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    public Sort getSorted(String sortBy) {
        Sort.Order order;
        if (sortBy.contains(REGEX)) {
            String[] fieldAndOrder = sortBy.split(REGEX);
            order = new Sort.Order(
                    Sort.Direction.valueOf(fieldAndOrder[ORDER_INDEX]),
                    fieldAndOrder[FIELD_INDEX]);
        } else {
            order = Sort.Order.asc(sortBy);
        }
        return Sort.by(order);
    }
}
