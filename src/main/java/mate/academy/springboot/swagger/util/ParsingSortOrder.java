package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class ParsingSortOrder {
    public static final String SORT_DELIMITER = ":";
    public static final String SORT_SEPARATE = ";";

    public static List<Sort.Order> getSortOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SORT_DELIMITER)) {
            String[] sortingFields = sortBy.split(SORT_SEPARATE);
            Sort.Order order;
            for (String field : sortingFields) {
                if (field.contains(SORT_DELIMITER)) {
                    String[] fieldsAndDirection = field.split(SORT_DELIMITER);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirection[1]), fieldsAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
