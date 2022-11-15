package util;

import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;

public class PageParseUtil {
    private static final String PARAMETER_SEPARATOR = ";";
    private static final String ATTRIBUTES_SEPARATOR = ":";

    public static List<Sort.Order> parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(ATTRIBUTES_SEPARATOR)) {
            String[] sortingFields = sortBy.split(PARAMETER_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(ATTRIBUTES_SEPARATOR)) {
                    String[] fieldsAndDirections = field.split(ATTRIBUTES_SEPARATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.DEFAULT_DIRECTION, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.DEFAULT_DIRECTION, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
