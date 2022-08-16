package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortOrderParser {
    private static final int SORTING_STRING_FIELD_INDEX = 0;
    private static final int SORTING_STRING_DIRECTION_INDEX = 1;
    private static final String FIELD_AND_DIRECTION_DELIMITER = ":";
    private static final String FIELDS_DELIMITER = ";";
    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;

    public Sort parseSortOrder(String sortingBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortingBy.contains(FIELDS_DELIMITER)
                || sortingBy.contains(FIELD_AND_DIRECTION_DELIMITER)) {
            String[] sortingFields = sortingBy.split(FIELDS_DELIMITER);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_AND_DIRECTION_DELIMITER)) {
                    String[] fieldsAndDirections = field.split(FIELD_AND_DIRECTION_DELIMITER);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[SORTING_STRING_DIRECTION_INDEX]),
                            fieldsAndDirections[SORTING_STRING_FIELD_INDEX]);
                } else {
                    order = new Sort.Order(DEFAULT_DIRECTION, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(DEFAULT_DIRECTION, sortingBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
