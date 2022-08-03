package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ProductSortOrderParser {
    private static final String SORTING_SEPARATOR = ";";
    private static final String SORTING_ORDER_SEPARATOR = ":";
    private static final int SORTING_VALUE_INDEX = 0;
    private static final int SORTING_ORDER_INDEX = 1;
    private static final Sort.Direction DEFAULT_SORTING = Sort.Direction.ASC;

    public List<Sort.Order> parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SORTING_ORDER_SEPARATOR)) {
            String[] sortingFields = sortBy.split(SORTING_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SORTING_ORDER_SEPARATOR)) {
                    String[] fieldsAndDirections = field.split(SORTING_ORDER_SEPARATOR);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldsAndDirections[SORTING_ORDER_INDEX]),
                            fieldsAndDirections[SORTING_VALUE_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(DEFAULT_SORTING, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
