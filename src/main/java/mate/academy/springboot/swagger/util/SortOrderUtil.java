package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortOrderUtil {
    private static final String SORTING_FIELD_SPLITTER = ";";
    private static final String SORTING_FIELD_AND_DIRECTION_SPLITTER = ":";
    private static final int DIRECTION_INDEX = 1;
    private static final int SORTING_FIELD_INDEX = 0;

    public Sort sortBy(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SORTING_FIELD_SPLITTER)) {
            String[] sortingFields = sortBy.split(SORTING_FIELD_SPLITTER);
            for (String field: sortingFields) {
                Sort.Order order;
                if (field.contains(SORTING_FIELD_AND_DIRECTION_SPLITTER)) {
                    String[] fieldsAndDirections = field.split(
                            SORTING_FIELD_AND_DIRECTION_SPLITTER);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[SORTING_FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
