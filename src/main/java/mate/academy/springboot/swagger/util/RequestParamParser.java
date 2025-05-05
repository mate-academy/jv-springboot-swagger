package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class RequestParamParser {
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    private static final String SORTING_FIELDS_SEPARATOR = ";";
    private static final String FIELD_AND_DIRECTION_SEPARATOR = ":";
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;

    public static List<Sort.Order> toSortOrders(String requestParam) {
        List<Sort.Order> orders = new ArrayList<>();
        if (requestParam.contains(FIELD_AND_DIRECTION_SEPARATOR)) {
            String[] sortingFields = requestParam.split(SORTING_FIELDS_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_AND_DIRECTION_SEPARATOR)) {
                    String[] fieldsAndDirections = field.split(FIELD_AND_DIRECTION_SEPARATOR);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(DEFAULT_SORT_DIRECTION, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(DEFAULT_SORT_DIRECTION, requestParam);
            orders.add(order);
        }
        return orders;
    }
}
