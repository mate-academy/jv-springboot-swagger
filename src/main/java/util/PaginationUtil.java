package util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
    private static final String SYMBOL_BETWEEN_ATTRIBUTES = ":";
    private static final String SYMBOL_BETWEEN_PARAMETERS = ";";
    private static final int INDEX_OF_FIELD_NAME = 0;
    private static final int INDEX_OF_DIRECTION_VALUE = 1;

    public static PageRequest getOrders(Integer page, Integer count, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SYMBOL_BETWEEN_ATTRIBUTES)) {
            String[] sortingFields = sortBy.split(SYMBOL_BETWEEN_PARAMETERS);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SYMBOL_BETWEEN_ATTRIBUTES)) {
                    String[] fieldsAndDirections = field.split(SYMBOL_BETWEEN_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[INDEX_OF_DIRECTION_VALUE]),
                            fieldsAndDirections[INDEX_OF_FIELD_NAME]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
