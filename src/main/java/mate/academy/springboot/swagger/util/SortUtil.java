package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortUtil {
    private static final int DIRECTION_INDEX = 1;
    private static final int PARAM_INDEX = 0;
    private static final String SEPARATOR_ATTRIBUTES = ":";
    private static final String SEPARATOR_PARAMS = ";";
    private static List<Sort.Order> orders;

    public static List<Sort.Order> sort(String sortBy) {
        orders = new ArrayList<>();
        if (sortBy.contains(SEPARATOR_ATTRIBUTES)) {
            String[] sortingFields = sortBy.split(SEPARATOR_PARAMS);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SEPARATOR_ATTRIBUTES)) {
                    String[] fieldsAndDirections = field.split(SEPARATOR_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[PARAM_INDEX]);
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
