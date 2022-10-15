package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class StringParser {
    public static final String SORT_ORDER_SEPARATOR = ":";
    public static final String FIELD_SORT_ORDER_SEPARATOR = ";";
    public static final Integer FIELD_INDEX = 0;
    public static final Integer SORT_ORDER_INDEX = 1;

    public List<Sort.Order> getSortOrders(String string) {
        List<Sort.Order> orders = new ArrayList<>();
        if (string.contains(SORT_ORDER_SEPARATOR)) {
            String [] sortingFields = string.split(FIELD_SORT_ORDER_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String [] fieldsAndDirections = field.split(SORT_ORDER_SEPARATOR);
                    order = new Sort.Order(Sort.Direction
                                    .valueOf(fieldsAndDirections[SORT_ORDER_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, string);
            orders.add(order);
        }
        return orders;
    }
}
