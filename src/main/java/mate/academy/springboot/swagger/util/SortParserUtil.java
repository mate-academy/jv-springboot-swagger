package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortParserUtil {
    public static final String FIELD_DIVIDER = ":";
    public static final String ORDER_FIELD_DIVIDER = ";";
    public static final int SORT_BY = 0;
    public static final int DIRECTION = 1;

    public static Sort sortParse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = sortBy.split(FIELD_DIVIDER);
        for (String field : sortingFields) {
            Sort.Order order;
            if (field.contains(ORDER_FIELD_DIVIDER)) {
                String[] fieldAndDirection = field.split(ORDER_FIELD_DIVIDER);
                order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[DIRECTION]),
                        fieldAndDirection[SORT_BY]);
            } else {
                order = new Sort.Order(Sort.Direction.ASC, field);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
