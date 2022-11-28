package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class Parser {
    private static final String ATTRIBUTE_SEPARATOR = ":";
    private static final String PARAM_SEPARATOR = ";";
    private static final int PARAM_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static List<Sort.Order> getSortOrders(String urlParams) {
        List<Sort.Order> orders = new ArrayList<>();
        if (urlParams.contains(ATTRIBUTE_SEPARATOR)) {
            String[] sortingFields = urlParams.split(PARAM_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(ATTRIBUTE_SEPARATOR)) {
                    String[] fieldsAndDirections = field.split(ATTRIBUTE_SEPARATOR);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[VALUE_INDEX]),
                            fieldsAndDirections[PARAM_INDEX]);
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
