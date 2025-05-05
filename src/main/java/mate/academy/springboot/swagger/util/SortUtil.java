package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortUtil {
    private static final int INDEX_OF_PARAMETER = 0;
    private static final int INDEX_OF_DIRECTION = 1;
    private static final String SYMBOL_BETWEEN_ATTRIBUTES = ":";
    private static final String SYMBOL_BETWEEN_PARAMETERS = ";";

    public static Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SYMBOL_BETWEEN_ATTRIBUTES)) {
            String[] sortingFields = sortBy.split(SYMBOL_BETWEEN_PARAMETERS);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SYMBOL_BETWEEN_ATTRIBUTES)) {
                    String[] fieldsAndDirection = field.split(SYMBOL_BETWEEN_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirection[INDEX_OF_DIRECTION]),
                            fieldsAndDirection[INDEX_OF_PARAMETER]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
