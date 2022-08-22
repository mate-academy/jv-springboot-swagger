package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {
    private static final String FIELDS_DELIMITER = ";";
    private static final String FIELD_DELIMITER = ":";
    private static final int FIELD = 0;
    private static final int DIRECTION = 1;

    public Sort toSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELD_DELIMITER)) {
            String[] sortingFields = sortBy.split(FIELDS_DELIMITER);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_DELIMITER)) {
                    String[] fieldAndDirections = field.split(FIELD_DELIMITER);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[DIRECTION]),
                            fieldAndDirections[FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
