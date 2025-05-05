package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public final class SortDirectionParser {
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;
    private static final String SORTING_CATEGORY_DELIMITER = ";";
    private static final String FIELD_DIRECTION_DELIMITER = ":";

    private SortDirectionParser() {
    }

    public static Sort by(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELD_DIRECTION_DELIMITER)) {
            String[] sortingFields = sortBy.split(SORTING_CATEGORY_DELIMITER);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_DIRECTION_DELIMITER)) {
                    String[] fieldAndDirection = field.split(FIELD_DIRECTION_DELIMITER);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldAndDirection[DIRECTION_INDEX]),
                            fieldAndDirection[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }
}
