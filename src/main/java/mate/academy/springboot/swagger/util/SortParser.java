package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortParser {
    private static final String COLON_REGEX = ":";
    private static final String SEMICOLON_REGEX = ";";
    private static final int INDEX_OF_DIRECTION = 1;
    private static final int INDEX_OF_FIELD = 0;

    public static List<Sort.Order> sortBy(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON_REGEX)) {
            String[] sortingFields = sortBy.split(SEMICOLON_REGEX);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON_REGEX)) {
                    String[] fieldsAndDirections = field.split(COLON_REGEX);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[INDEX_OF_DIRECTION]),
                            fieldsAndDirections[INDEX_OF_FIELD]);
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
