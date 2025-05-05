package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class Parser {
    private static final String SEPARATOR_ATTRIBUTES = ":";
    private static final String SEPARATOR_PARAMS = ";";
    private static final int INDEX_FIELD_NAME = 0;
    private static final int INDEX_DIRECTION_VALUE = 1;

    public static List<Order> getSortOrders(String urlParams) {
        if (urlParams.contains(SEPARATOR_ATTRIBUTES)) {
            String[] sortingFields = urlParams.split(SEPARATOR_PARAMS);
            List<Sort.Order> orders = new ArrayList<>();
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SEPARATOR_ATTRIBUTES)) {
                    String[] fieldParts = field.split(SEPARATOR_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldParts[INDEX_DIRECTION_VALUE]),
                            fieldParts[INDEX_FIELD_NAME]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
            return orders;
        } else {
            return List.of(new Sort.Order(Sort.Direction.DESC, urlParams));
        }
    }
}
