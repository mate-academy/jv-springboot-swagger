package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortParser {
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;
    private static final String CHAR_BETWEEN_FIELD_AND_DIRECTION = ":";
    private static final String CHAR_BETWEEN_SORTING_FIELDS = ";";

    public static Sort getSort(String sortingExpression) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = sortingExpression.split(CHAR_BETWEEN_SORTING_FIELDS);
        for (String sortingField : sortingFields) {
            Sort.Order order;
            if (sortingExpression.contains(CHAR_BETWEEN_FIELD_AND_DIRECTION)) {
                String[] fieldAndOrder = sortingField.split(CHAR_BETWEEN_FIELD_AND_DIRECTION);
                order = new Sort.Order(Sort.Direction.fromString(fieldAndOrder[DIRECTION_INDEX]),
                        fieldAndOrder[FIELD_INDEX]);
            } else {
                order = Sort.Order.asc(sortingExpression);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
