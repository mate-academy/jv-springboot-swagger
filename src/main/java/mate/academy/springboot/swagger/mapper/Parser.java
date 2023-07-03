package mate.academy.springboot.swagger.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class Parser {
    private static final String SEPARATOR_ATTRIBUTES = ":";
    private static final String SEPARATOR_PARAMS = ";";
    private static final int INDEX_FIELD_NAME = 0;
    private static final int INDEX_DIRECTION_VALUE = 1;

    public static List<Sort.Order> getSortOrders(String urlParams) {
        List<Sort.Order> orders = new ArrayList<>();
        if (urlParams.contains(SEPARATOR_ATTRIBUTES)) {
            String[] sortingFields = urlParams.split(SEPARATOR_PARAMS);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SEPARATOR_ATTRIBUTES)) {
                    String[] fieldsAndDirections = field.split(SEPARATOR_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[INDEX_DIRECTION_VALUE]),
                            fieldsAndDirections[INDEX_FIELD_NAME]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, urlParams));
        }
        return orders;
    }
}
