package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class Sorter {
    private static final int INDEX_OF_PARAM_NAME = 0;
    private static final int INDEX_OF_PARAM_VALUE = 1;
    private static final String SEPARATOR_ATTRIBUTES = ":";
    private static final String SEPARATOR_PARAMS = ";";

    public List<Sort.Order> sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SEPARATOR_ATTRIBUTES)) {
            String[] sortingFields = sortBy.split(SEPARATOR_PARAMS);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SEPARATOR_ATTRIBUTES)) {
                    String[] fieldsAndDirections = field.split(SEPARATOR_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[INDEX_OF_PARAM_VALUE]),
                            fieldsAndDirections[INDEX_OF_PARAM_NAME]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
