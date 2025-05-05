package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {
    private static final String FIELD_SPLIT = ":";
    private static final String ORDER_FIELD_SPLIT = ";";
    private static final int SORT_BY = 0;
    private static final int DIRECTION = 1;

    public static List<Sort.Order> sortBy(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELD_SPLIT)) {
            String[] sortingFields = sortBy.split(ORDER_FIELD_SPLIT);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_SPLIT)) {
                    String[] fieldsAndDirections = field.split(FIELD_SPLIT);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldsAndDirections[DIRECTION]),
                            fieldsAndDirections[SORT_BY]);
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
