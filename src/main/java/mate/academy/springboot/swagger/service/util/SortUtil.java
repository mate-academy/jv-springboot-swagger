package mate.academy.springboot.swagger.service.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    private static final Sort.Direction DEFAULT_ORDER_DIRECTION = Sort.Direction.ASC;
    private static final String ORDER_SEPARATOR = ":";
    private static final int ORDER_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    public Sort parseSorting(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(ORDER_SEPARATOR)) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(ORDER_SEPARATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[ORDER_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(DEFAULT_ORDER_DIRECTION, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(DEFAULT_ORDER_DIRECTION, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return sort;
    }
}
