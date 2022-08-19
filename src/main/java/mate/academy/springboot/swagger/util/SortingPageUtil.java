package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortingPageUtil {
    private static final String DELIMITER = ";";
    private static final String SEPARATOR = ":";
    private static final int DIRECTION = 1;
    private static final int FIELD = 0;

    public static Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SEPARATOR)) {
            String[] sortingFields = sortBy.split(DELIMITER);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SEPARATOR)) {
                    String[] fieldsAndDirections = field.split(SEPARATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[DIRECTION]),
                            fieldsAndDirections[FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
