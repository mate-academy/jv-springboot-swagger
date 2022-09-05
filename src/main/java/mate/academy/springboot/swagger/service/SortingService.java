package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortingService {
    public static final String DELIMITER = ":";
    public static final String SEPARATOR = ";";
    private static final int FIELD = 0;
    private static final int DIRECTION = 1;

    public static Sort sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(DELIMITER)) {
            String[] sortingFields = sortBy.split(SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(DELIMITER)) {
                    String[] fieldsAndDirections = field.split(DELIMITER);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[DIRECTION]),
                            fieldsAndDirections[FIELD]);
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
