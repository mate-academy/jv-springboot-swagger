package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortService {
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;
    private static final String FIELDS_SEPARATOR = ";";
    private static final String FIELD_AND_DIRECTION_SEPARATOR = ":";

    public static Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = sortBy.split(FIELDS_SEPARATOR);
        for (String sortingField : sortingFields) {
            Sort.Order order;
            if (sortBy.contains(FIELD_AND_DIRECTION_SEPARATOR)) {
                String[] fieldAndOrder = sortingField.split(FIELD_AND_DIRECTION_SEPARATOR);
                order = new Sort.Order(Sort.Direction.fromString(fieldAndOrder[DIRECTION_INDEX]),
                        fieldAndOrder[FIELD_INDEX]);
            } else {
                order = Sort.Order.asc(sortBy);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
